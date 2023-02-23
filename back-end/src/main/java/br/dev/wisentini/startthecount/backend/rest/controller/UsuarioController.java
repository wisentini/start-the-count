package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.config.JwtService;
import br.dev.wisentini.startthecount.backend.rest.dto.creation.UsuarioCreationDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.request.AuthenticationRequestDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.response.ApiEmptyResponse;
import br.dev.wisentini.startthecount.backend.rest.dto.response.AuthenticationResponseDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UsuarioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.update.UsuarioUpdateDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.UsuarioMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Papel;
import br.dev.wisentini.startthecount.backend.rest.model.Usuario;
import br.dev.wisentini.startthecount.backend.rest.service.BoletimUrnaUsuarioService;
import br.dev.wisentini.startthecount.backend.rest.service.PapelUsuarioService;
import br.dev.wisentini.startthecount.backend.rest.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/usuarios")
@RequiredArgsConstructor
@Tag(name = "usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;

    private final BoletimUrnaUsuarioService boletimUrnaUsuarioService;

    private final PapelUsuarioService papelUsuarioService;

    private final BoletimUrnaMapper boletimUrnaMapper;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Operation(
        summary = "Encontra um usuário pelo username.",
        description = "Encontra um usuário pelo username.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(value = "/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    public UsuarioRetrievalDTO findUsuario(
        @Parameter(description = "O username do usuário que deve ser encontrado.")
        @PathVariable("username") String username
    ) {
        return this.usuarioMapper.toUsuarioRetrievalDTO(this.usuarioService.findByUsername(username));
    }

    @Operation(
        summary = "Encontra todos os usuários.",
        description = "Encontra todos os usuários.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<UsuarioRetrievalDTO> findUsuarios() {
        return this.usuarioService
            .findAll()
            .stream()
            .map(this.usuarioMapper::toUsuarioRetrievalDTO)
            .collect(Collectors.toList());
    }

    @Operation(
        summary = "Encontra todos os boletins de urna coletados por um usuário.",
        description = "Encontra todos os boletins de urna coletados por um usuário.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(value = "/{username}/boletins-urna")
    @ResponseStatus(value = HttpStatus.OK)
    public List<BoletimUrnaRetrievalDTO> findBoletinsUrna(
        @Parameter(description = "O username do usuário.")
        @PathVariable("username") String username
    ) {
        return this.boletimUrnaUsuarioService
            .findBoletinsUrnaByUsuario(username)
            .stream()
            .map(this.boletimUrnaMapper::toBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }

    @Operation(
        summary = "Encontra todos os papéis de um usuário.",
        description = "Encontra todos os papéis de um usuário.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(value = "/{username}/papeis")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Papel> findPapeis(
        @Parameter(description = "O username.")
        @PathVariable("username") String username
    ) {
        return this.papelUsuarioService.findPapeisByUsuario(username);
    }

    @Operation(
        summary = "Atualiza um usuário pelo username.",
        description = "Atualiza um usuário pelo username.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping(value = "/{username}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void updateUsuario(
        @Parameter(description = "O username do usuário que deve ser atualizado.")
        @PathVariable("username")
        String username,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Os novos dados do usuário.")
        @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        this.usuarioService.updateByUsername(username, usuarioUpdateDTO);
    }

    @Operation(summary = "Cria um usuário.", description = "Cria um usuário.")
    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public AuthenticationResponseDTO createUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Os dados do novo usuário.")
        @Valid @RequestBody UsuarioCreationDTO usuarioCreationDTO
    ) {
        return new AuthenticationResponseDTO(
            this.jwtService.generateToken(
                this.usuarioService.save(usuarioCreationDTO)
            )
        );
    }

    @Operation(
        summary = "Remove um usuário.",
        description = "Remove um usuário.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping(value = "/{username}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deleteUsuario(
        @Parameter(description = "O username do usuário que deve ser removido.")
        @PathVariable("username") String username
    ) {
        this.usuarioService.deleteByUsername(username);
    }

    @Operation(summary = "Autentica um usuário.", description = "Autentica um usuário.")
    @PostMapping(value = "/login")
    @ResponseStatus(value = HttpStatus.CREATED)
    public AuthenticationResponseDTO login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Os dados de autenticação do usuário.")
        @Valid @RequestBody AuthenticationRequestDTO authenticationRequestDTO
    ) {
        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.getUsername(),
                authenticationRequestDTO.getSenha()
            )
        );

        return new AuthenticationResponseDTO(
            this.jwtService.generateToken(
                this.usuarioService.findByUsername(authenticationRequestDTO.getUsername())
            )
        );
    }

    @Operation(
        summary = "Retorna o usuário atual.",
        description = "Retorna o usuário atual.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(value = "/current")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> current(@AuthenticationPrincipal Usuario usuario) {
        if (Objects.isNull(usuario)) {
            return new ResponseEntity<>(new ApiEmptyResponse(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            this.usuarioMapper.toUsuarioRetrievalDTO(usuario),
            HttpStatus.OK
        );
    }
}
