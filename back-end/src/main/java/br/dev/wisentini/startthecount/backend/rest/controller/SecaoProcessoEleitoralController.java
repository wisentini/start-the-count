package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoProcessoEleitoralIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoProcessoEleitoralRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoProcessoEleitoralMapper;
import br.dev.wisentini.startthecount.backend.rest.service.SecaoProcessoEleitoralService;

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
@RequestMapping(value = "/secoes-processos-eleitorais")
@RequiredArgsConstructor
@Tag(name = "secoes-processos-eleitorais")
@SecurityRequirement(name = "bearerAuth")
public class SecaoProcessoEleitoralController {

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    private final SecaoProcessoEleitoralMapper secaoProcessoEleitoralMapper;

    @Operation(summary = "Encontra uma relação entre seção e processo eleitoral.", description = "Encontra uma relação entre seção e processo eleitoral.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public SecaoProcessoEleitoralRetrievalDTO findSecaoProcessoEleitoral(
        @Parameter(description = "Os dados que identificam a relação entre seção e processo eleitoral.")
        @Valid SecaoProcessoEleitoralIdDTO id
    ) {
        return this.secaoProcessoEleitoralMapper
            .toSecaoProcessoEleitoralRetrievalDTO(this.secaoProcessoEleitoralService.findById(id));
    }

    @Operation(summary = "Encontra todas as relações entre seção e processo eleitoral.", description = "Encontra todas as relações entre seção e processo eleitoral.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<SecaoProcessoEleitoralRetrievalDTO> findSecoesProcessosEleitorais() {
        return this.secaoProcessoEleitoralService
            .findAll()
            .stream()
            .map(this.secaoProcessoEleitoralMapper::toSecaoProcessoEleitoralRetrievalDTO)
            .collect(Collectors.toList());
    }
}
