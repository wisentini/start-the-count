package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaUsuarioIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaUsuarioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaUsuarioMapper;
import br.dev.wisentini.startthecount.backend.rest.service.BoletimUrnaUsuarioService;

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
@RequestMapping(value = "/boletins-urna-usuarios")
@RequiredArgsConstructor
@Tag(name = "boletins-urna-usuarios")
@SecurityRequirement(name = "bearerAuth")
public class BoletimUrnaUsuarioController {

    private final BoletimUrnaUsuarioService boletimUrnaUsuarioService;

    private final BoletimUrnaUsuarioMapper boletimUrnaUsuarioMapper;

    @Operation(summary = "Encontra uma relação entre boletim de urna e usuário.", description = "Encontra uma relação entre boletim de urna e usuário.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public BoletimUrnaUsuarioRetrievalDTO findBoletimUrnaUsuario(
        @Parameter(description = "Os dados que identificam a relação entre boletim de urna e usuário.")
        @Valid BoletimUrnaUsuarioIdDTO id
    ) {
        return this.boletimUrnaUsuarioMapper.toBoletimUrnaUsuarioRetrievalDTO(this.boletimUrnaUsuarioService.findById(id));
    }

    @Operation(summary = "Encontra todas as relações entre boletim de urna e usuário.", description = "Encontra todas as relações entre boletim de urna e usuário.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<BoletimUrnaUsuarioRetrievalDTO> findBoletinsUrnaUsuarios() {
        return this.boletimUrnaUsuarioService
            .findAll()
            .stream()
            .map(this.boletimUrnaUsuarioMapper::toBoletimUrnaUsuarioRetrievalDTO)
            .collect(Collectors.toList());
    }
}
