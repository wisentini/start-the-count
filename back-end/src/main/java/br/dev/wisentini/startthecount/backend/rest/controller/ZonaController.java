package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ZonaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.LocalVotacaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ZonaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.LocalVotacaoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.ZonaMapper;
import br.dev.wisentini.startthecount.backend.rest.service.ZonaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/zonas")
@RequiredArgsConstructor
@Tag(name = "zonas")
@SecurityRequirement(name = "bearerAuth")
public class ZonaController {

    private final ZonaService zonaService;

    private final ZonaMapper zonaMapper;

    private final SecaoMapper secaoMapper;

    private final LocalVotacaoMapper localVotacaoMapper;

    @Operation(summary = "Encontra uma zona.", description = "Encontra uma zona.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public ZonaRetrievalDTO findZona(
        @Parameter(description = "Os dados que identificam a zona.")
        @Valid ZonaIdDTO id
    ) {
        return this.zonaMapper.toZonaRetrievalDTO(this.zonaService.findById(id));
    }

    @Operation(summary = "Encontra todas as zonas.", description = "Encontra todas as zonas.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ZonaRetrievalDTO> findZonas() {
        return this.zonaService
            .findAll()
            .stream()
            .map(this.zonaMapper::toZonaRetrievalDTO)
            .collect(Collectors.toList());
    }

    @Operation(summary = "Encontra todas as seções de uma zona.", description = "Encontra todas as seções de uma zona.")
    @GetMapping(value = "/secoes")
    @ResponseStatus(value = HttpStatus.OK)
    public List<SecaoRetrievalDTO> findSecoes(
        @Parameter(description = "Os dados que identificam a zona.")
        @Valid ZonaIdDTO id
    ) {
        return this.zonaService
            .findSecoes(id)
            .stream()
            .map(this.secaoMapper::toSecaoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @Operation(summary = "Encontra todos os locais de votação de uma zona.", description = "Encontra todos os locais de votação de uma zona.")
    @GetMapping(value = "/locais-votacao")
    @ResponseStatus(value = HttpStatus.OK)
    public List<LocalVotacaoRetrievalDTO> findLocaisVotacao(
        @Parameter(description = "Os dados que identificam a zona.")
        @Valid ZonaIdDTO id
    ) {
        return this.zonaService
            .findLocaisVotacao(id)
            .stream()
            .map(this.localVotacaoMapper::toLocalVotacaoRetrievalDTO)
            .collect(Collectors.toList());
    }
}
