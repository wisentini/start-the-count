package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.BoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.model.UrnaEletronica;
import br.dev.wisentini.startthecount.backend.rest.repository.UrnaEletronicaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "urna-eletronica")
public class UrnaEletronicaService {

    private final UrnaEletronicaRepository urnaEletronicaRepository;

    private final BoletimUrnaService boletimUrnaService;

    private final CachingService cachingService;

    @Cacheable(key = "#numeroSerie")
    public UrnaEletronica findByNumeroSerie(Integer numeroSerie) {
        return this.urnaEletronicaRepository
            .findByNumeroSerie(numeroSerie)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma urna eletrônica com o número de série %d.", numeroSerie));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<UrnaEletronica> findAll() {
        return this.urnaEletronicaRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d', #root.methodName, #numeroSerie)")
    public Set<BoletimUrna> findBoletinsUrna(Integer numeroSerie) {
        return this.findByNumeroSerie(numeroSerie).getBoletinsUrna();
    }

    public UrnaEletronica getIfExistsOrElseSave(UrnaEletronica urnaEletronica) {
        if (this.urnaEletronicaRepository.existsByNumeroSerie(urnaEletronica.getNumeroSerie())) {
            return this.findByNumeroSerie(urnaEletronica.getNumeroSerie());
        }

        this.cachingService.evictAllCaches();

        return this.urnaEletronicaRepository.save(urnaEletronica);
    }

    public void deleteByNumeroSerie(Integer numeroSerie) {
        if (!this.urnaEletronicaRepository.existsByNumeroSerie(numeroSerie)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma urna eletrônica com o número de série %d.", numeroSerie));
        }

        this.boletimUrnaService.deleteByUrnaEletronica(numeroSerie);

        this.urnaEletronicaRepository.deleteByNumeroSerie(numeroSerie);

        this.cachingService.evictAllCaches();
    }
}
