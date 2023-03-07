package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.ApuracaoVotosPartidoBoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.model.Candidatura;
import br.dev.wisentini.startthecount.backend.rest.repository.PartidoRepository;
import br.dev.wisentini.startthecount.backend.rest.model.Partido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "pleito")
public class PartidoService {

    private final PartidoRepository partidoRepository;

    private final CandidaturaService candidaturaService;

    private final ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService;

    private final CachingService cachingService;

    @Autowired
    public PartidoService(
        PartidoRepository partidoRepository,
        @Lazy CandidaturaService candidaturaService,
        ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService,
        CachingService cachingService
    ) {
        this.partidoRepository = partidoRepository;
        this.candidaturaService = candidaturaService;
        this.apuracaoVotosPartidoBoletimUrnaService = apuracaoVotosPartidoBoletimUrnaService;
        this.cachingService = cachingService;
    }

    @Cacheable(key = "#numeroTSE")
    public Partido findByNumeroTSE(Integer numeroTSE) {
        return this.partidoRepository
            .findByNumeroTSE(numeroTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum partido com o número %d.", numeroTSE));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<Partido> findAll() {
        return this.partidoRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d', #root.methodName, #numeroTSE)")
    public Set<Candidatura> findCandidaturas(Integer numeroTSE) {
        return this.findByNumeroTSE(numeroTSE).getCandidaturas();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d', #root.methodName, #numeroTSE)")
    public Set<ApuracaoVotosPartidoBoletimUrna> findApuracoesVotosBoletimUrna(Integer numeroTSE) {
        return this.findByNumeroTSE(numeroTSE).getApuracoesVotosBoletinsUrna();
    }

    public Partido getIfExistsOrElseSave(Partido partido) {
        if (this.partidoRepository.existsByNumeroTSE(partido.getNumeroTSE())) {
            return this.findByNumeroTSE(partido.getNumeroTSE());
        }

        this.cachingService.evictAllCaches();

        return this.partidoRepository.save(partido);
    }

    public void deleteByNumeroTSE(Integer numeroTSE) {
        if (!this.partidoRepository.existsByNumeroTSE(numeroTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum partido com o número %d.", numeroTSE));
        }

        this.candidaturaService.deleteByPartido(numeroTSE);
        this.apuracaoVotosPartidoBoletimUrnaService.deleteByPartido(numeroTSE);

        this.partidoRepository.deleteByNumeroTSE(numeroTSE);

        this.cachingService.evictAllCaches();
    }
}
