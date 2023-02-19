package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosPartidoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosPartidoService;

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
@RequestMapping(value = "/apuracoes-votos-partidos")
@RequiredArgsConstructor
@Tag(name = "apuracoes-votos-partidos")
@SecurityRequirement(name = "bearerAuth")
public class ApuracaoVotosPartidoController {

    private final ApuracaoVotosPartidoService apuracaoVotosPartidoService;

    @Operation(summary = "Encontra as apurações de votos de partidos.", description = "Encontra as apurações de votos de partidos.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApuracaoVotosPartidoRetrievalDTO> findApuracoesVotosPartidos() {
        return this.apuracaoVotosPartidoService.findAll();
    }
}
