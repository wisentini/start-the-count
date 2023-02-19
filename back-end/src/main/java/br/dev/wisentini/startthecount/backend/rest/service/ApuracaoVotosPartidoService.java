package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosPartidoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.repository.ApuracaoVotosPartidoBoletimUrnaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApuracaoVotosPartidoService {

    private final ApuracaoVotosPartidoBoletimUrnaRepository apuracaoVotosPartidoBoletimUrnaRepository;

    public List<ApuracaoVotosPartidoRetrievalDTO> findAll() {
        return this.apuracaoVotosPartidoBoletimUrnaRepository.findApuracaoVotosPartidos();
    }
}
