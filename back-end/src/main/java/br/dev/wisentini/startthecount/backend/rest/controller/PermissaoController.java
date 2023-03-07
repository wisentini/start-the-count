package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PapelRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PermissaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.PapelMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.PermissaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.PermissaoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/permissoes")
@RequiredArgsConstructor
public class PermissaoController {

    private final PermissaoService permissaoService;

    private final PermissaoMapper permissaoMapper;

    private final PapelMapper papelMapper;

    @GetMapping(value = "/{nome}")
    @ResponseStatus(value = HttpStatus.OK)
    public PermissaoRetrievalDTO findPermissao(@PathVariable("nome") String nome) {
        return this.permissaoMapper.toPermissaoRetrievalDTO(this.permissaoService.findByNome(nome));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PermissaoRetrievalDTO> findPermissoes() {
        return this.permissaoService
            .findAll()
            .stream()
            .map(this.permissaoMapper::toPermissaoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{nome}/papeis")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<PapelRetrievalDTO> findPapeis(@PathVariable("nome") String nome) {
        return this.permissaoService
            .findPapeis(nome)
            .stream()
            .map(this.papelMapper::toPapelRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
