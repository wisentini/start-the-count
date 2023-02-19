package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Pleito;
import br.dev.wisentini.startthecount.backend.rest.model.ProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.service.SecaoPleitoService;
import br.dev.wisentini.startthecount.backend.rest.service.SecaoProcessoEleitoralService;
import br.dev.wisentini.startthecount.backend.rest.service.SecaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/secoes")
@RequiredArgsConstructor
@Tag(name = "secoes")
@SecurityRequirement(name = "bearerAuth")
public class SecaoController {

    private final SecaoService secaoService;

    private final SecaoPleitoService secaoPleitoService;

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    private final SecaoMapper secaoMapper;

    @Operation(summary = "Encontra uma seção.", description = "Encontra uma seção.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public SecaoRetrievalDTO findSecao(
        @Parameter(description = "Os dados que identificam a seção.")
        @Valid SecaoIdDTO id
    ) {
        return this.secaoMapper.toSecaoRetrievalDTO(this.secaoService.findById(id));
    }

    @Operation(summary = "Encontra todas as seções.", description = "Encontra todas as seções.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<SecaoRetrievalDTO> findSecoes() {
        return this.secaoService
            .findAll()
            .stream()
            .map(this.secaoMapper::toSecaoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @Operation(summary = "Encontra todos os pleitos de uma seção.", description = "Encontra todos os pleitos de uma seção.")
    @GetMapping(value = "/pleitos")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Pleito> findPleitos(
        @Parameter(description = "Os dados que identificam a seção.")
        @Valid SecaoIdDTO id
    ) {
        return this.secaoPleitoService.findPleitosBySecao(id);
    }

    @Operation(summary = "Encontra todos os processos eleitorais de uma seção.", description = "Encontra todos os processos eleitorais de uma seção.")
    @GetMapping(value = "/processos-eleitorais")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProcessoEleitoral> findProcessosEleitorais(
        @Parameter(description = "Os dados que identificam a seção.")
        @Valid SecaoIdDTO id
    ) {
        return this.secaoProcessoEleitoralService.findProcessosEleitoraisBySecao(id);
    }
}
