package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CandidaturaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.CandidaturaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Partido;
import br.dev.wisentini.startthecount.backend.rest.service.PartidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/partidos")
@RequiredArgsConstructor
@Tag(name = "partidos")
@SecurityRequirement(name = "bearerAuth")
public class PartidoController {

    private final PartidoService partidoService;

    private final CandidaturaMapper candidaturaMapper;

    @Operation(summary = "Encontra um partido.", description = "Encontra um partido.")
    @GetMapping(value = "/{numeroTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public Partido findPartido(
        @Parameter(description = "O número do partido.")
        @PathVariable("numeroTSE") int numeroTSE
    ) {
        return this.partidoService.findByNumeroTSE(numeroTSE);
    }

    @Operation(summary = "Encontra todos os partidos.", description = "Encontra todos os partidos.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Partido> findPartidos() {
        return this.partidoService.findAll();
    }

    @Operation(summary = "Encontra todas as candidaturas de um partido.", description = "Encontra todas as candidaturas de um partido.")
    @GetMapping(value = "/{numeroTSE}/candidaturas")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CandidaturaRetrievalDTO> findCandidaturas(
        @Parameter(description = "O número do partido.")
        @PathVariable("numeroTSE") int numeroTSE
    ) {
        return this.partidoService
            .findCandidaturas(numeroTSE)
            .stream()
            .map(this.candidaturaMapper::toCandidaturaRetrievalDTO)
            .collect(Collectors.toList());
    }
}
