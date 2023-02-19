package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.model.TipoCargo;
import br.dev.wisentini.startthecount.backend.rest.service.TipoCargoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tipos-cargo")
@RequiredArgsConstructor
@Tag(name = "tipos-cargo")
@SecurityRequirement(name = "bearerAuth")
public class TipoCargoController {

    private final TipoCargoService tipoCargoService;

    @Operation(summary = "Encontra um tipo de cargo.", description = "Encontra um tipo de cargo.")
    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public TipoCargo findTipoCargo(
        @Parameter(description = "O nome do tipo de cargo que deve ser encontrado.")
        @PathVariable("codigoTSE") int codigoTSE
    ) {
        return this.tipoCargoService.findByCodigoTSE(codigoTSE);
    }

    @Operation(summary = "Encontra todos os tipos de cargo.", description = "Encontra todos os tipos de cargo.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<TipoCargo> findTiposCargo() {
        return this.tipoCargoService.findAll();
    }
}
