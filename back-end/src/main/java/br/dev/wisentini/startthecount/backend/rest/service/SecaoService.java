package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.ZonaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.*;
import br.dev.wisentini.startthecount.backend.rest.repository.SecaoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "secao")
public class SecaoService {

    private final SecaoRepository secaoRepository;

    private final SecaoMapper secaoMapper;

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    private final SecaoPleitoService secaoPleitoService;

    private final AgregacaoSecaoService agregacaoSecaoService;

    private final CachingService cachingService;

    @Cacheable(key = "T(java.lang.String).format('%d:%d:%s', #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF)")
    public Secao findById(SecaoIdDTO id) {
        return this.secaoRepository
            .findByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSESecao(), id.getNumeroTSEZona(), id.getSiglaUF())
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma seção identificada por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<Secao> findAll() {
        return this.secaoRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF)")
    public Set<ProcessoEleitoral> findProcessosEleitorais(SecaoIdDTO id) {
        return this
            .findById(id)
            .getProcessosEleitorais()
            .stream()
            .map(SecaoProcessoEleitoral::getProcessoEleitoral)
            .collect(Collectors.toSet());
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF)")
    public Set<Pleito> findPleitos(SecaoIdDTO id) {
        return this
            .findById(id)
            .getPleitos()
            .stream()
            .map(SecaoPleito::getPleito)
            .collect(Collectors.toSet());
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF)")
    public Set<Secao> findSecoesPrincipais(SecaoIdDTO id) {
        return this
            .findById(id)
            .getSecoesPrincipais()
            .stream()
            .map(AgregacaoSecao::getSecaoPrincipal)
            .collect(Collectors.toSet());
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF)")
    public Set<Secao> findSecoesAgregadas(SecaoIdDTO id) {
        return this
            .findById(id)
            .getSecoesAgregadas()
            .stream()
            .map(AgregacaoSecao::getSecaoAgregada)
            .collect(Collectors.toSet());
    }

    public Secao getIfExistsOrElseSave(Secao secao) {
        if (this.secaoRepository.existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            secao.getNumeroTSE(),
            secao.getZona().getNumeroTSE(),
            secao.getZona().getUF().getSigla()
        )) {
            return this.findById(this.secaoMapper.toSecaoIdDTO(secao));
        }

        this.cachingService.evictAllCaches();

        return this.secaoRepository.save(secao);
    }

    public void deleteById(SecaoIdDTO id) {
        id.validate();

        if (!this.secaoRepository.existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSESecao(), id.getNumeroTSEZona(), id.getSiglaUF())) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma seção identificada por %s.", id));
        }

        this.secaoProcessoEleitoralService.deleteBySecao(id);
        this.secaoPleitoService.deleteBySecao(id);
        this.agregacaoSecaoService.deleteBySecaoAgregada(id);
        this.agregacaoSecaoService.deleteBySecaoPrincipal(id);

        this.secaoRepository.deleteByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteByZona(ZonaIdDTO zonaId) {
        zonaId.validate();

        this.secaoRepository
            .findByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(zonaId.getNumeroTSEZona(), zonaId.getSiglaUF())
            .forEach(secao -> {
                SecaoIdDTO id = this.secaoMapper.toSecaoIdDTO(secao);

                this.secaoProcessoEleitoralService.deleteBySecao(id);
                this.secaoPleitoService.deleteBySecao(id);
                this.agregacaoSecaoService.deleteBySecaoAgregada(id);
                this.agregacaoSecaoService.deleteBySecaoPrincipal(id);
            });

        this.secaoRepository.deleteByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            zonaId.getNumeroTSEZona(),
            zonaId.getSiglaUF()
        );

        this.cachingService.evictAllCaches();
    }
}
