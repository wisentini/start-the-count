package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosCandidaturaService;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCandidaturaRetrievalDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/apuracoes-votos-candidaturas")
@RequiredArgsConstructor
@Tag(name = "apuracoes-votos-candidaturas")
@SecurityRequirement(name = "bearerAuth")
public class ApuracaoVotosCandidaturaController {

    private final ApuracaoVotosCandidaturaService apuracaoVotosCandidaturaService;

    @Operation(summary = "Encontra as apurações de votos de candidaturas.", description = "Encontra as apurações de votos de candidaturas.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApuracaoVotosCandidaturaRetrievalDTO> findApuracoesVotosCandidaturas() {
        return this.apuracaoVotosCandidaturaService.findAll();
    }
}
