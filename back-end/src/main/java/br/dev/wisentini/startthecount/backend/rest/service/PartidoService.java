package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Candidatura;
import br.dev.wisentini.startthecount.backend.rest.repository.PartidoRepository;
import br.dev.wisentini.startthecount.backend.rest.model.Partido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;

    private final CandidaturaService candidaturaService;

    private final ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService;

    @Autowired
    public PartidoService(PartidoRepository partidoRepository, @Lazy CandidaturaService candidaturaService, ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService) {
        this.partidoRepository = partidoRepository;
        this.candidaturaService = candidaturaService;
        this.apuracaoVotosPartidoBoletimUrnaService = apuracaoVotosPartidoBoletimUrnaService;
    }

    public Partido findByNumeroTSE(int numeroTSE) {
        return this.partidoRepository
            .findByNumeroTSE(numeroTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum partido com o número %d.", numeroTSE));
            });
    }

    public List<Partido> findAll() {
        return this.partidoRepository.findAll();
    }

    public List<Candidatura> findCandidaturas(Integer numeroTSEPartido) {
        return this.candidaturaService.findByPartido(numeroTSEPartido);
    }

    public Partido getIfExistsOrElseSave(Partido partido) {
        if (this.partidoRepository.existsByNumeroTSE(partido.getNumeroTSE())) {
            return this.findByNumeroTSE(partido.getNumeroTSE());
        }

        return this.partidoRepository.save(partido);
    }

    public void deleteByNumeroTSE(int numeroTSE) {
        if (!this.partidoRepository.existsByNumeroTSE(numeroTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum partido com o número %d.", numeroTSE));
        }

        this.candidaturaService.deleteByPartido(numeroTSE);
        this.apuracaoVotosPartidoBoletimUrnaService.deleteByPartido(numeroTSE);

        this.partidoRepository.deleteByNumeroTSE(numeroTSE);
    }
}
