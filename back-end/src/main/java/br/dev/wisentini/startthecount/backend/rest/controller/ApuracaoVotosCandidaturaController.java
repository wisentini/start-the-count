package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCandidaturaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosCandidaturaService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/apuracoes-votos-candidaturas")
@RequiredArgsConstructor
public class ApuracaoVotosCandidaturaController {

    private final ApuracaoVotosCandidaturaService apuracaoVotosCandidaturaService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApuracaoVotosCandidaturaRetrievalDTO> findApuracoesVotosCandidaturas() {
        return this.apuracaoVotosCandidaturaService.findAll();
    }
}
