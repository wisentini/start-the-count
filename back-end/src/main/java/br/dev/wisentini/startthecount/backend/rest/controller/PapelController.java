package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UsuarioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.UsuarioMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Papel;
import br.dev.wisentini.startthecount.backend.rest.model.Permissao;
import br.dev.wisentini.startthecount.backend.rest.service.PapelPermissaoService;
import br.dev.wisentini.startthecount.backend.rest.service.PapelService;

import br.dev.wisentini.startthecount.backend.rest.service.PapelUsuarioService;
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
@RequestMapping(value = "/papeis")
@RequiredArgsConstructor
@Tag(name = "papeis")
@SecurityRequirement(name = "bearerAuth")
public class PapelController {

    private final PapelService papelService;

    private final PapelPermissaoService papelPermissaoService;

    private final PapelUsuarioService papelUsuarioService;

    private final UsuarioMapper usuarioMapper;

    @Operation(summary = "Encontra um papel.", description = "Encontra um papel.")
    @GetMapping(value = "/{nome}")
    @ResponseStatus(value = HttpStatus.OK)
    public Papel findPapel(
        @Parameter(description = "O nome do papel que deve ser encontrado.")
        @PathVariable("nome") String nome
    ) {
        return this.papelService.findByNome(nome);
    }

    @Operation(summary = "Encontra todos os papéis.", description = "Encontra todos os papéis.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Papel> findPapeis() {
        return this.papelService.findAll();
    }

    @Operation(summary = "Encontra todas as permissões de um papel.", description = "Encontra todas as permissões de um papel.")
    @GetMapping(value = "/{nome}/permissoes")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Permissao> findPermissoes(
        @Parameter(description = "O nome do papel.")
        @PathVariable("nome") String nome
    ) {
        return this.papelPermissaoService.findPermissoesByPapel(nome);
    }

    @Operation(summary = "Encontra todos os usuários de um papel.", description = "Encontra todos os usuários de um papel.")
    @GetMapping(value = "/{nome}/usuarios")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UsuarioRetrievalDTO> findUsuarios(
        @Parameter(description = "O nome do papel.")
        @PathVariable("nome") String nome
    ) {
        return this.papelUsuarioService
            .findUsuariosByPapel(nome)
            .stream()
            .map(this.usuarioMapper::toUsuarioRetrievalDTO)
            .collect(Collectors.toList());
    }
}
