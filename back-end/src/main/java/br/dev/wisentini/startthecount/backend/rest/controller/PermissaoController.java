package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.model.Papel;
import br.dev.wisentini.startthecount.backend.rest.model.Permissao;
import br.dev.wisentini.startthecount.backend.rest.service.PapelPermissaoService;
import br.dev.wisentini.startthecount.backend.rest.service.PermissaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/permissoes")
@RequiredArgsConstructor
@Tag(name = "permissoes")
@SecurityRequirement(name = "bearerAuth")
public class PermissaoController {

    private final PermissaoService permissaoService;

    private final PapelPermissaoService papelPermissaoService;

    @Operation(summary = "Encontra uma permissão.", description = "Encontra uma permissão.")
    @GetMapping(value = "/{nome}")
    @ResponseStatus(value = HttpStatus.OK)
    public Permissao findPermissao(
        @Parameter(description = "O nome da permissão que deve ser encontrada.")
        @PathVariable("nome") String nome
    ) {
        return this.permissaoService.findByNome(nome);
    }

    @Operation(summary = "Encontra todas as permissões.", description = "Encontra todas as permissões.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Permissao> findPermissoes() {
        return this.permissaoService.findAll();
    }

    @Operation(summary = "Encontra todos os papéis que possuem determinada permissão.", description = "Encontra todos os papéis que possuem determinada permissão.")
    @GetMapping(value = "/{nome}/papeis")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Papel> findPapeis(
        @Parameter(description = "O nome da permissão.")
        @PathVariable("nome") String nome
    ) {
        return this.papelPermissaoService.findPapeisByPermissao(nome);
    }
}
