package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.LocalVotacaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.LocalVotacaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Municipio;
import br.dev.wisentini.startthecount.backend.rest.service.MunicipioService;

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
@RequestMapping(value = "/municipios")
@RequiredArgsConstructor
@Tag(name = "municipios")
@SecurityRequirement(name = "bearerAuth")
public class MunicipioController {

    private final MunicipioService municipioService;

    private final LocalVotacaoMapper localVotacaoMapper;

    @Operation(summary = "Encontra um município.", description = "Encontra um município.")
    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public Municipio findMunicipio(
        @Parameter(description = "O código do município.")
        @PathVariable("codigoTSE") Integer codigoTSE
    ) {
        return this.municipioService.findByCodigoTSE(codigoTSE);
    }

    @Operation(summary = "Encontra todos os municípios.", description = "Encontra todos os municípios.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Municipio> findMunicipios() {
        return this.municipioService.findAll();
    }

    @Operation(summary = "Encontra todos os locais de votação de um município.", description = "Encontra todos os locais de votação de um município.")
    @GetMapping(value = "/{codigoTSE}/locais-votacao")
    @ResponseStatus(value = HttpStatus.OK)
    public List<LocalVotacaoRetrievalDTO> findLocaisVotacao(
        @Parameter(description = "O código do município.")
        @PathVariable("codigoTSE") Integer codigoTSE
    ) {
        return this.municipioService
            .findLocaisVotacao(codigoTSE)
            .stream()
            .map(this.localVotacaoMapper::toLocalVotacaoRetrievalDTO)
            .collect(Collectors.toList());
    }
}
