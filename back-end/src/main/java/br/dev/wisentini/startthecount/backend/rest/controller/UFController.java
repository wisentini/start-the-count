package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ZonaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.ZonaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Municipio;
import br.dev.wisentini.startthecount.backend.rest.model.UF;
import br.dev.wisentini.startthecount.backend.rest.service.UFService;

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
@RequestMapping(value = "/ufs")
@RequiredArgsConstructor
@Tag(name = "ufs")
@SecurityRequirement(name = "bearerAuth")
public class UFController {

    private final UFService ufService;

    private final ZonaMapper zonaMapper;

    @Operation(summary = "Encontra uma UF pela sigla.", description = "Encontra uma UF pela sigla.")
    @GetMapping(value = "/{sigla}")
    @ResponseStatus(value = HttpStatus.OK)
    public UF findUF(
        @Parameter(description = "A sigla da UF que deve ser encontrada.")
        @PathVariable("sigla") String sigla
    ) {
        return this.ufService.findBySigla(sigla);
    }

    @Operation(summary = "Encontra todas as UFs.", description = "Encontra todas as UFs.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<UF> findUFs() {
        return this.ufService.findAll();
    }

    @Operation(summary = "Encontra todas as zonas de uma UF.", description = "Encontra todas as zonas de uma UF.")
    @GetMapping(value = "/{sigla}/zonas")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ZonaRetrievalDTO> findZonas(
        @Parameter(description = "A sigla da UF.")
        @PathVariable("sigla") String sigla
    ) {
        return this.ufService
            .findZonas(sigla)
            .stream()
            .map(this.zonaMapper::toZonaRetrievalDTO)
            .collect(Collectors.toList());
    }

    @Operation(summary = "Encontra todos os municípios de uma UF.", description = "Encontra todos os municípios de uma UF.")
    @GetMapping(value = "/{sigla}/municipios")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Municipio> findMunicipios(
        @Parameter(description = "A sigla da UF.")
        @PathVariable("sigla") String sigla
    ) {
        return this.ufService.findMunicipios(sigla);
    }
}
