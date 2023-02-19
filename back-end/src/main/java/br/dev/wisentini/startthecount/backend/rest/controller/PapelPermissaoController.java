package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.PapelPermissaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PapelPermissaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.PapelPermissaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.PapelPermissaoService;

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
@RequestMapping(value = "/papeis-permissoes")
@RequiredArgsConstructor
@Tag(name = "papeis-permissoes")
@SecurityRequirement(name = "bearerAuth")
public class PapelPermissaoController {

    private final PapelPermissaoService papelPermissaoService;

    private final PapelPermissaoMapper papelPermissaoMapper;

    @Operation(summary = "Encontra uma relação entre papel e permissão.", description = "Encontra uma relação entre papel e permissão.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public PapelPermissaoRetrievalDTO findPapelPermissao(
        @Parameter(description = "Os dados que identificam a relação entre papel e permissão.")
        @Valid PapelPermissaoIdDTO id
    ) {
        return this.papelPermissaoMapper.toPapelPermissaoRetrievalDTO(this.papelPermissaoService.findById(id));
    }

    @Operation(summary = "Encontra todas as relações entre papel e permissão.", description = "Encontra todas as relações entre papel e permissão.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PapelPermissaoRetrievalDTO> findPapeisPermissoes() {
        return this.papelPermissaoService
            .findAll()
            .stream()
            .map(this.papelPermissaoMapper::toPapelPermissaoRetrievalDTO)
            .collect(Collectors.toList());
    }
}
