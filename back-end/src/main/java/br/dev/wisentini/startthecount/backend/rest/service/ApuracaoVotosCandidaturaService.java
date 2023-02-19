package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.repository.ApuracaoVotosCandidaturaBoletimUrnaRepository;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCandidaturaRetrievalDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApuracaoVotosCandidaturaService {

    private final ApuracaoVotosCandidaturaBoletimUrnaRepository apuracaoVotosCandidaturaBoletimUrnaRepository;

    public List<ApuracaoVotosCandidaturaRetrievalDTO> findAll() {
        return this.apuracaoVotosCandidaturaBoletimUrnaRepository.findApuracaoVotosCandidaturas();
    }
}
