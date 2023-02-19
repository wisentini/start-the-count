package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCargoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosCargoService;

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
@RequestMapping(value = "/apuracoes-votos-cargos")
@RequiredArgsConstructor
@Tag(name = "apuracoes-votos-cargos")
@SecurityRequirement(name = "bearerAuth")
public class ApuracaoVotosCargoController {

    private final ApuracaoVotosCargoService apuracaoVotosCargoService;

    @Operation(summary = "Encontra as apurações de votos de cargos.", description = "Encontra as apurações de votos de cargos.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApuracaoVotosCargoRetrievalDTO> findApuracoesVotosCargos() {
        return this.apuracaoVotosCargoService.findAll();
    }
}
