package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Pleito;
import br.dev.wisentini.startthecount.backend.rest.service.PleitoService;
import br.dev.wisentini.startthecount.backend.rest.service.SecaoPleitoService;

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
@RequestMapping(value = "/pleitos")
@RequiredArgsConstructor
@Tag(name = "pleitos")
@SecurityRequirement(name = "bearerAuth")
public class PleitoController {

    private final PleitoService pleitoService;

    private final SecaoPleitoService secaoPleitoService;

    private final SecaoMapper secaoMapper;

    @Operation(summary = "Encontra um pleito.", description = "Encontra um pleito.")
    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public Pleito findPleito(
        @Parameter(description = "O código do pleito que deve ser encontrado.")
        @PathVariable("codigoTSE") int codigoTSE
    ) {
        return this.pleitoService.findByCodigoTSE(codigoTSE);
    }

    @Operation(summary = "Encontra todos os pleitos.", description = "Encontra todos os pleitos.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Pleito> findPleitos() {
        return this.pleitoService.findAll();
    }

    @Operation(summary = "Encontra todas as seções de um pleito.", description = "Encontra todas as seções de um pleito.")
    @GetMapping(value = "/{codigoTSE}/secoes")
    @ResponseStatus(value = HttpStatus.OK)
    public List<SecaoRetrievalDTO> findSecoes(
        @Parameter(description = "O código do pleito.")
        @PathVariable("codigoTSE") int codigoTSE
    ) {
        return this.secaoPleitoService
            .findSecoesByPleito(codigoTSE)
            .stream()
            .map(this.secaoMapper::toSecaoRetrievalDTO)
            .collect(Collectors.toList());
    }
}
