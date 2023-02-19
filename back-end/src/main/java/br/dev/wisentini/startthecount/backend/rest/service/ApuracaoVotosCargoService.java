package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCargoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.repository.ApuracaoVotosCargoBoletimUrnaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApuracaoVotosCargoService {

    private final ApuracaoVotosCargoBoletimUrnaRepository apuracaoVotosCargoBoletimUrnaRepository;

    public List<ApuracaoVotosCargoRetrievalDTO> findAll() {
        return this.apuracaoVotosCargoBoletimUrnaRepository.findApuracaoVotosCargos();
    }
}
