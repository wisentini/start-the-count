package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.CargoEleicao;
import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;
import br.dev.wisentini.startthecount.backend.rest.repository.CargoRepository;
import br.dev.wisentini.startthecount.backend.rest.model.Cargo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "cargo")
public class CargoService {

    private final CargoRepository cargoRepository;

    private final CargoEleicaoService cargoEleicaoService;

    private final CachingService cachingService;

    @Autowired
    public CargoService(
        CargoRepository cargoRepository,
        @Lazy CargoEleicaoService cargoEleicaoService,
        CachingService cachingService
    ) {
        this.cargoRepository = cargoRepository;
        this.cargoEleicaoService = cargoEleicaoService;
        this.cachingService = cachingService;
    }

    @Cacheable(key = "#codigoTSE")
    public Cargo findByCodigoTSE(int codigoTSE) {
        return this.cargoRepository
            .findByCodigoTSE(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum cargo com o código %d.", codigoTSE));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<Cargo> findAll() {
        return this.cargoRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d', #root.methodName, #codigoTSE)")
    public Set<Eleicao> findEleicoes(Integer codigoTSE) {
        return this
            .findByCodigoTSE(codigoTSE)
            .getEleicoes()
            .stream()
            .map(CargoEleicao::getEleicao)
            .collect(Collectors.toSet());
    }

    public Cargo getIfExistsOrElseSave(Cargo cargo) {
        if (this.cargoRepository.existsByCodigoTSE(cargo.getCodigoTSE())) {
            return this.findByCodigoTSE(cargo.getCodigoTSE());
        }

        this.cachingService.evictAllCaches();

        return this.cargoRepository.save(cargo);
    }

    public void deleteByCodigoTSE(int codigoTSE) {
        if (!this.cargoRepository.existsByCodigoTSE(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum cargo com o código %d.", codigoTSE));
        }

        this.cargoEleicaoService.deleteByCargo(codigoTSE);

        this.cargoRepository.deleteByCodigoTSE(codigoTSE);

        this.cachingService.evictAllCaches();
    }

    public void deleteByTipo(int codigoTSETipoCargo) {
        this.cargoRepository
            .findByTipoCodigoTSE(codigoTSETipoCargo)
            .forEach(cargo -> this.cargoEleicaoService.deleteByCargo(cargo.getCodigoTSE()));

        this.cargoRepository.deleteByTipoCodigoTSE(codigoTSETipoCargo);

        this.cachingService.evictAllCaches();
    }
}
