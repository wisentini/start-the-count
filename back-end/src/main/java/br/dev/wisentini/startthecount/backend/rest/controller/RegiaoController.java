package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.model.Regiao;
import br.dev.wisentini.startthecount.backend.rest.model.UF;
import br.dev.wisentini.startthecount.backend.rest.service.RegiaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/regioes")
@RequiredArgsConstructor
@Tag(name = "regioes")
@SecurityRequirement(name = "bearerAuth")
public class RegiaoController {

    private final RegiaoService regiaoService;

    @Operation(summary = "Encontra uma região.", description = "Encontra uma região.")
    @GetMapping(value = "/{sigla}")
    @ResponseStatus(value = HttpStatus.OK)
    public Regiao findRegiao(
        @Parameter(description = "A sigla da região.")
        @PathVariable("sigla") String sigla
    ) {
        return this.regiaoService.findBySigla(sigla);
    }

    @Operation(summary = "Encontra todas as regiões.", description = "Encontra todas as regiões.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Regiao> findRegioes() {
        return this.regiaoService.findAll();
    }

    @Operation(summary = "Encontra todas as UFs de uma região.", description = "Encontra todas as UFs de uma região.")
    @GetMapping(value = "/{sigla}/ufs")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UF> findUFs(
        @Parameter(description = "A sigla da região.")
        @PathVariable("sigla") String sigla
    ) {
        return this.regiaoService.findUFs(sigla);
    }
}
