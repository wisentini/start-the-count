package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Candidato;
import br.dev.wisentini.startthecount.backend.rest.model.Candidatura;
import br.dev.wisentini.startthecount.backend.rest.repository.CandidatoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "candidato")
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;

    private final CandidaturaService candidaturaService;

    private final CachingService cachingService;

    @Autowired
    public CandidatoService(
        CandidatoRepository candidatoRepository,
        @Lazy CandidaturaService candidaturaService,
        CachingService cachingService
    ) {
        this.candidatoRepository = candidatoRepository;
        this.candidaturaService = candidaturaService;
        this.cachingService = cachingService;
    }

    @Cacheable(key = "#codigoTSE")
    public Candidato findByCodigoTSE(String codigoTSE) {
        return this.candidatoRepository
            .findByCodigoTSEEqualsIgnoreCase(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum candidato com o código %s.", codigoTSE));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<Candidato> findAll() {
        return this.candidatoRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d', #root.methodName, #codigoTSE)")
    public Set<Candidatura> findCandidaturas(String codigoTSE) {
        return this.findByCodigoTSE(codigoTSE).getCandidaturas();
    }

    public Candidato getIfExistsOrElseSave(Candidato candidato) {
        if (this.candidatoRepository.existsByCodigoTSEEqualsIgnoreCase(candidato.getCodigoTSE())) {
            return this.findByCodigoTSE(candidato.getCodigoTSE());
        }

        this.cachingService.evictAllCaches();

        return this.candidatoRepository.save(candidato);
    }

    public void deleteByCodigoTSE(String codigoTSE) {
        if (!this.candidatoRepository.existsByCodigoTSEEqualsIgnoreCase(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum candidato com o código %s.", codigoTSE));
        }

        this.candidaturaService.deleteByCandidato(codigoTSE);

        this.candidatoRepository.deleteByCodigoTSEEqualsIgnoreCase(codigoTSE);

        this.cachingService.evictAllCaches();
    }
}
