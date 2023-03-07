package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PapelRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PermissaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UsuarioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.PapelMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.PermissaoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.UsuarioMapper;
import br.dev.wisentini.startthecount.backend.rest.service.PapelService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/papeis")
@RequiredArgsConstructor
public class PapelController {

    private final PapelService papelService;

    private final PapelMapper papelMapper;

    private final PermissaoMapper permissaoMapper;

    private final UsuarioMapper usuarioMapper;

    @GetMapping(value = "/{nome}")
    @ResponseStatus(value = HttpStatus.OK)
    public PapelRetrievalDTO findPapel(@PathVariable("nome") String nome) {
        return this.papelMapper.toPapelRetrievalDTO(this.papelService.findByNome(nome));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PapelRetrievalDTO> findPapeis() {
        return this.papelService
            .findAll()
            .stream()
            .map(this.papelMapper::toPapelRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{nome}/permissoes")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<PermissaoRetrievalDTO> findPermissoes(@PathVariable("nome") String nome) {
        return this.papelService
            .findPermissoes(nome)
            .stream()
            .map(this.permissaoMapper::toPermissaoRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/{nome}/usuarios")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<UsuarioRetrievalDTO> findUsuarios(@PathVariable("nome") String nome) {
        return this.papelService
            .findUsuarios(nome)
            .stream()
            .map(this.usuarioMapper::toUsuarioRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
