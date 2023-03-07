package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.config.JwtService;
import br.dev.wisentini.startthecount.backend.rest.dto.creation.UsuarioCreationDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.request.AuthenticationRequestDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.response.ApiEmptyResponse;
import br.dev.wisentini.startthecount.backend.rest.dto.response.ApiResponse;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PapelRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UsuarioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.update.UsuarioUpdateDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.PapelMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.UsuarioMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Usuario;
import br.dev.wisentini.startthecount.backend.rest.service.UsuarioService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;

    private final BoletimUrnaMapper boletimUrnaMapper;

    private final PapelMapper papelMapper;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @GetMapping(value = "/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    public UsuarioRetrievalDTO findUsuario(@PathVariable("username") String username) {
        return this.usuarioMapper.toUsuarioRetrievalDTO(this.usuarioService.findByUsername(username));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Set<UsuarioRetrievalDTO> findUsuarios() {
        return this.usuarioService
            .findAll()
            .stream()
            .map(this.usuarioMapper::toUsuarioRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/{username}/boletins-urna")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<BoletimUrnaRetrievalDTO> findBoletinsUrna(@PathVariable("username") String username) {
        return this.usuarioService
            .findBoletinsUrna(username)
            .stream()
            .map(this.boletimUrnaMapper::toBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/{username}/papeis")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<PapelRetrievalDTO> findPapeis(@PathVariable("username") String username) {
        return this.usuarioService
            .findPapeis(username)
            .stream()
            .map(this.papelMapper::toPapelRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @PatchMapping(value = "/{username}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void update(
        @PathVariable("username") String username,
        @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO
    ) {
        this.usuarioService.updateByUsername(username, usuarioUpdateDTO);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ApiResponse<String> create(@Valid @RequestBody UsuarioCreationDTO usuarioCreationDTO) {
        return new ApiResponse<>(
            this.jwtService.generateToken(
                this.usuarioService.save(usuarioCreationDTO)
            ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(value = "/{username}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable("username") String username) {
        this.usuarioService.deleteByUsername(username);
    }

    @PostMapping(value = "/login")
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<String> login(@Valid @RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.getUsername(),
                authenticationRequestDTO.getSenha()
            )
        );

        return new ApiResponse<>(
            this.jwtService.generateToken(
                this.usuarioService.findByUsername(authenticationRequestDTO.getUsername())
            ),
            HttpStatus.OK
        );
    }

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
