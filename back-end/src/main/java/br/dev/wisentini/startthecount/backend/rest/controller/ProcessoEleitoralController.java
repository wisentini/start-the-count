package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.ProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.service.ProcessoEleitoralService;

import br.dev.wisentini.startthecount.backend.rest.service.SecaoProcessoEleitoralService;
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
@RequestMapping(value = "/processos-eleitorais")
@RequiredArgsConstructor
@Tag(name = "processos-eleitorais")
@SecurityRequirement(name = "bearerAuth")
public class ProcessoEleitoralController {

    private final ProcessoEleitoralService processoEleitoralService;

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    private final SecaoMapper secaoMapper;

    @Operation(summary = "Encontra um processo eleitoral.", description = "Encontra um processo eleitoral.")
    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProcessoEleitoral findProcessoEleitoral(
        @Parameter(description = "O código do processo eleitoral que deve ser encontrado.")
        @PathVariable("codigoTSE") int codigoTSE
    ) {
        return this.processoEleitoralService.findByCodigoTSE(codigoTSE);
    }

    @Operation(summary = "Encontra todos os processos eleitorais.", description = "Encontra todos os processos eleitorais.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProcessoEleitoral> findProcessosEleitorais() {
        return this.processoEleitoralService.findAll();
    }

    @Operation(summary = "Encontra todas as seções de um processo eleitoral.", description = "Encontra todas as seções de um processo eleitoral.")
    @GetMapping(value = "/{codigoTSE}/secoes")
    @ResponseStatus(value = HttpStatus.OK)
    public List<SecaoRetrievalDTO> findSecoes(
        @Parameter(description = "O código do processoEleitoral.")
        @PathVariable("codigoTSE") int codigoTSE
    ) {
        return this.secaoProcessoEleitoralService
            .findSecoesByProcessoEleitoral(codigoTSE)
            .stream()
            .map(this.secaoMapper::toSecaoRetrievalDTO)
            .collect(Collectors.toList());
    }
}
