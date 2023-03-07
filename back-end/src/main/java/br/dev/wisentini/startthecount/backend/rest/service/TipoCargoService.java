package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Cargo;
import br.dev.wisentini.startthecount.backend.rest.repository.TipoCargoRepository;
import br.dev.wisentini.startthecount.backend.rest.model.TipoCargo;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "tipo-cargo")
public class TipoCargoService {

    private final TipoCargoRepository tipoCargoRepository;

    private final CargoService cargoService;

    private final CachingService cachingService;

    @Cacheable(key = "#codigoTSE")
    public TipoCargo findByCodigoTSE(Integer codigoTSE) {
        return this.tipoCargoRepository
            .findByCodigoTSE(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum tipo de cargo com o código do TSE %d.", codigoTSE));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<TipoCargo> findAll() {
        return this.tipoCargoRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d', #root.methodName, #codigoTSE)")
    public Set<Cargo> findCargos(Integer codigoTSE) {
        return this.findByCodigoTSE(codigoTSE).getCargos();
    }

    public TipoCargo getIfExistsOrElseSave(TipoCargo tipoCargo) {
        if (this.tipoCargoRepository.existsByCodigoTSE(tipoCargo.getCodigoTSE())) {
            return this.findByCodigoTSE(tipoCargo.getCodigoTSE());
        }

        this.cachingService.evictAllCaches();

        return this.tipoCargoRepository.save(tipoCargo);
    }

    public void deleteByCodigoTSE(Integer codigoTSE) {
        if (!this.tipoCargoRepository.existsByCodigoTSE(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum tipo de cargo com o código %d.", codigoTSE));
        }

        this.cargoService.deleteByTipo(codigoTSE);

        this.tipoCargoRepository.deleteByCodigoTSE(codigoTSE);

        this.cachingService.evictAllCaches();
    }
}
