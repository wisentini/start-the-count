package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.repository.LocalVotacaoRepository;
import br.dev.wisentini.startthecount.backend.rest.dto.id.LocalVotacaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.ZonaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.LocalVotacaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.LocalVotacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "local-votacao")
public class LocalVotacaoService {

    private final LocalVotacaoRepository localVotacaoRepository;

    private final LocalVotacaoMapper localVotacaoMapper;

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    private final CachingService cachingService;

    @Autowired
    public LocalVotacaoService(
        LocalVotacaoRepository localVotacaoRepository,
        @Lazy LocalVotacaoMapper localVotacaoMapper,
        SecaoProcessoEleitoralService secaoProcessoEleitoralService,
        CachingService cachingService
    ) {
        this.localVotacaoRepository = localVotacaoRepository;
        this.localVotacaoMapper = localVotacaoMapper;
        this.secaoProcessoEleitoralService = secaoProcessoEleitoralService;
        this.cachingService = cachingService;
    }

    @Cacheable(key = "T(java.lang.String).format('%d:%d:%s', #id.numeroTSELocalVotacao, #id.numeroTSEZona, #id.siglaUF)")
    public LocalVotacao findById(LocalVotacaoIdDTO id) {
        id.validate();

        return this.localVotacaoRepository
            .findByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSELocalVotacao(), id.getNumeroTSEZona(), id.getSiglaUF())
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum local de votação identificado por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<LocalVotacao> findAll() {
        return this.localVotacaoRepository.findAll();
    }
    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s', #root.methodName, #id.numeroTSELocalVotacao, #id.numeroTSEZona, #id.siglaUF)")
    public Set<Secao> findSecoes(LocalVotacaoIdDTO id) {
        return this
            .findById(id)
            .getSecoes()
            .stream()
            .map(SecaoProcessoEleitoral::getSecao)
            .collect(Collectors.toSet());
    }

    public LocalVotacao getIfExistsOrElseSave(LocalVotacao localVotacao) {
        if (this.localVotacaoRepository.existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            localVotacao.getNumeroTSE(),
            localVotacao.getZona().getNumeroTSE(),
            localVotacao.getZona().getUF().getSigla()
        )) {
            return this.findById(this.localVotacaoMapper.toLocalVotacaoIdDTO(localVotacao));
        }

        this.cachingService.evictAllCaches();

        return this.localVotacaoRepository.save(localVotacao);
    }

    public void deleteById(LocalVotacaoIdDTO id) {
        id.validate();

        if (!this.localVotacaoRepository.existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSELocalVotacao(), id.getNumeroTSEZona(), id.getSiglaUF())) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum local de votação identificado por %s.", id));
        }

        this.secaoProcessoEleitoralService.deleteByLocalVotacao(id);

        this.localVotacaoRepository.deleteByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSELocalVotacao(), id.getNumeroTSEZona(), id.getSiglaUF());

        this.cachingService.evictAllCaches();
    }

    public void deleteByZona(ZonaIdDTO zonaId) {
        zonaId.validate();

        this.localVotacaoRepository
            .findByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(zonaId.getNumeroTSEZona(), zonaId.getSiglaUF())
            .forEach(localVotacao -> this.secaoProcessoEleitoralService.deleteByLocalVotacao(
                this.localVotacaoMapper.toLocalVotacaoIdDTO(localVotacao)
            ));

        this.localVotacaoRepository.deleteByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(zonaId.getNumeroTSEZona(), zonaId.getSiglaUF());

        this.cachingService.evictAllCaches();
    }

    public void deleteByMunicipio(int codigoTSEMunicipio) {
        this.localVotacaoRepository
            .findByMunicipioCodigoTSE(codigoTSEMunicipio)
            .forEach(localVotacao -> this.secaoProcessoEleitoralService.deleteByLocalVotacao(
                this.localVotacaoMapper.toLocalVotacaoIdDTO(localVotacao)
            ));

        this.localVotacaoRepository.deleteByMunicipioCodigoTSE(codigoTSEMunicipio);

        this.cachingService.evictAllCaches();
    }
}
