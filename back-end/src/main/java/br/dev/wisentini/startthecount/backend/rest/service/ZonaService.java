package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ZonaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.ZonaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.LocalVotacao;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;
import br.dev.wisentini.startthecount.backend.rest.model.Zona;
import br.dev.wisentini.startthecount.backend.rest.repository.ZonaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "zona")
public class ZonaService {

    private final ZonaRepository zonaRepository;

    private final ZonaMapper zonaMapper;

    private final LocalVotacaoService localVotacaoService;

    private final SecaoService secaoService;

    private final CachingService cachingService;

    @Autowired
    public ZonaService(
        ZonaRepository zonaRepository,
        ZonaMapper zonaMapper,
        LocalVotacaoService localVotacaoService,
        @Lazy SecaoService secaoService,
        CachingService cachingService
    ) {
        this.zonaRepository = zonaRepository;
        this.zonaMapper = zonaMapper;
        this.localVotacaoService = localVotacaoService;
        this.secaoService = secaoService;
        this.cachingService = cachingService;
    }

    @Cacheable(key = "T(java.lang.String).format('%d:%s', #id.numeroTSEZona, #id.siglaUF)")
    public Zona findById(ZonaIdDTO id) {
        id.validate();

        return this.zonaRepository
            .findByNumeroTSEAndUfSiglaEqualsIgnoreCase(id.getNumeroTSEZona(), id.getSiglaUF())
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma zona identificada por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<Zona> findAll() {
        return this.zonaRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%s', #root.methodName, #id.numeroTSEZona, #id.siglaUF)")
    public Set<Secao> findSecoes(ZonaIdDTO id) {
        return this.findById(id).getSecoes();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%s', #root.methodName, #id.numeroTSEZona, #id.siglaUF)")
    public Set<LocalVotacao> findLocaisVotacao(ZonaIdDTO id) {
        return this.findById(id).getLocaisVotacao();
    }

    public Zona getIfExistsOrElseSave(Zona zona) {
        if (this.zonaRepository.existsByNumeroTSEAndUfSiglaEqualsIgnoreCase(zona.getNumeroTSE(), zona.getUF().getSigla())) {
            return this.findById(this.zonaMapper.toZonaIdDTO(zona));
        }

        this.cachingService.evictAllCaches();

        return this.zonaRepository.save(zona);
    }

    public void deleteById(ZonaIdDTO id) {
        id.validate();

        if (!this.zonaRepository.existsByNumeroTSEAndUfSiglaEqualsIgnoreCase(id.getNumeroTSEZona(), id.getSiglaUF())) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma zona identificada por %s.", id));
        }

        this.secaoService.deleteByZona(id);
        this.localVotacaoService.deleteByZona(id);

        this.zonaRepository.deleteByNumeroTSEAndUfSiglaEqualsIgnoreCase(id.getNumeroTSEZona(), id.getSiglaUF());

        this.cachingService.evictAllCaches();
    }
}
