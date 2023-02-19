package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.model.Cargo;
import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;
import br.dev.wisentini.startthecount.backend.rest.service.CargoEleicaoService;
import br.dev.wisentini.startthecount.backend.rest.service.EleicaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/eleicoes")
@RequiredArgsConstructor
@Tag(name = "eleicoes")
@SecurityRequirement(name = "bearerAuth")
public class EleicaoController {

    private final EleicaoService eleicaoService;

    private final CargoEleicaoService cargoEleicaoService;

    @Operation(summary = "Encontra uma eleição.", description = "Encontra uma eleição.")
    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public Eleicao findEleicao(
        @Parameter(description = "O código da eleição que deve ser encontrada.")
        @PathVariable("codigoTSE") int codigoTSE
    ) {
        return this.eleicaoService.findByCodigoTSE(codigoTSE);
    }

    @Operation(summary = "Encontra todas as eleições.", description = "Encontra todas as eleições.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Eleicao> findEleicoes() {
        return this.eleicaoService.findAll();
    }

    @Operation(summary = "Encontra todos os cargos de uma eleição.", description = "Encontra todos os cargos de uma eleição.")
    @GetMapping(value = "/{codigoTSE}/cargos")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Cargo> findCargos(
        @Parameter(description = "O código da eleição.")
        @PathVariable("codigoTSE") int codigoTSE
    ) {
        return this.cargoEleicaoService.findCargosByEleicao(codigoTSE);
    }
}
