package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;
import br.dev.wisentini.startthecount.backend.rest.model.Pleito;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoPleito;
import br.dev.wisentini.startthecount.backend.rest.repository.PleitoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "pleito")
public class PleitoService {

    private final PleitoRepository pleitoRepository;

    private final EleicaoService eleicaoService;

    private final SecaoPleitoService secaoPleitoService;

    private final CachingService cachingService;

    @Cacheable(key = "#codigoTSE")
    public Pleito findByCodigoTSE(Integer codigoTSE) {
        return this.pleitoRepository
            .findByCodigoTSE(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum pleito com o código %d.", codigoTSE));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<Pleito> findAll() {
        return this.pleitoRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d', #root.methodName, #codigoTSE)")
    public Set<Secao> findSecoes(Integer codigoTSE) {
        return this
            .findByCodigoTSE(codigoTSE)
            .getSecoes()
            .stream()
            .map(SecaoPleito::getSecao)
            .collect(Collectors.toSet());
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d', #root.methodName, #codigoTSE)")
    public Set<Eleicao> findEleicoes(Integer codigoTSE) {
        return this.findByCodigoTSE(codigoTSE).getEleicoes();
    }

    public Pleito getIfExistsOrElseSave(Pleito pleito) {
        if (this.pleitoRepository.existsByCodigoTSE(pleito.getCodigoTSE())) {
            return this.findByCodigoTSE(pleito.getCodigoTSE());
        }

        this.cachingService.evictAllCaches();

        return this.pleitoRepository.save(pleito);
    }

    public void deleteByCodigoTSE(Integer codigoTSE) {
        if (!this.pleitoRepository.existsByCodigoTSE(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum pleito com o código %d.", codigoTSE));
        }

        this.eleicaoService.deleteByPleito(codigoTSE);
        this.secaoPleitoService.deleteByPleito(codigoTSE);

        this.pleitoRepository.deleteByCodigoTSE(codigoTSE);

        this.cachingService.evictAllCaches();
    }

    public void deleteByProcessoEleitoral(Integer codigoTSEProcessoEleitoral) {
        this.pleitoRepository.findByProcessoEleitoralCodigoTSE(codigoTSEProcessoEleitoral)
            .forEach(pleito -> {
                this.eleicaoService.deleteByPleito(pleito.getCodigoTSE());
                this.secaoPleitoService.deleteByPleito(pleito.getCodigoTSE());
            });

        this.pleitoRepository.deleteByProcessoEleitoralCodigoTSE(codigoTSEProcessoEleitoral);

        this.cachingService.evictAllCaches();
    }
}
