package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.PapelPermissaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PapelPermissaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.PapelPermissaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.PapelPermissaoService;

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
@RequestMapping(value = "/api/papeis-permissoes")
@RequiredArgsConstructor
public class PapelPermissaoController {

    private final PapelPermissaoService papelPermissaoService;

    private final PapelPermissaoMapper papelPermissaoMapper;

    @GetMapping(params = {"nomePapel", "nomePermissao"})
    @ResponseStatus(value = HttpStatus.OK)
    public PapelPermissaoRetrievalDTO findPapelPermissao(@Valid PapelPermissaoIdDTO id) {
        return this.papelPermissaoMapper.toPapelPermissaoRetrievalDTO(this.papelPermissaoService.findById(id));
    }

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
