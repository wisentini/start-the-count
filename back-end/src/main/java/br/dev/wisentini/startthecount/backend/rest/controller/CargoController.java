package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.model.Cargo;
import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;
import br.dev.wisentini.startthecount.backend.rest.service.CargoEleicaoService;
import br.dev.wisentini.startthecount.backend.rest.service.CargoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cargos")
@RequiredArgsConstructor
@Tag(name = "cargos")
@SecurityRequirement(name = "bearerAuth")
public class CargoController {

    private final CargoService cargoService;

    private final CargoEleicaoService cargoEleicaoService;

    @Operation(summary = "Encontra um cargo.", description = "Encontra um cargo.")
    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public Cargo findCargo(
        @Parameter(description = "O código do cargo que deve ser encontrado.")
        @PathVariable("codigoTSE") int codigoTSE
    ) {
        return this.cargoService.findByCodigoTSE(codigoTSE);
    }

    @Operation(summary = "Encontra todos os cargos.", description = "Encontra todos os cargos.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Cargo> findCargos() {
        return this.cargoService.findAll();
    }

    @Operation(summary = "Encontra todas as eleições de um cargo.", description = "Encontra todas as eleições de um cargo.")
    @GetMapping(value = "/{codigoTSE}/eleicoes")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Eleicao> findEleicoes(
        @Parameter(description = "O código do cargo.")
        @PathVariable("codigoTSE") int codigoTSE
    ) {
        return this.cargoEleicaoService.findEleicoesByCargo(codigoTSE);
    }
}
