package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaUsuarioIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaUsuarioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaUsuarioMapper;
import br.dev.wisentini.startthecount.backend.rest.service.BoletimUrnaUsuarioService;

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
@RequestMapping(value = "/api/boletins-urna-usuarios")
@RequiredArgsConstructor
public class BoletimUrnaUsuarioController {

    private final BoletimUrnaUsuarioService boletimUrnaUsuarioService;

    private final BoletimUrnaUsuarioMapper boletimUrnaUsuarioMapper;

    @GetMapping(params = {"username", "numeroTSESecao", "numeroTSEZona", "siglaUF", "codigoTSEPleito"})
    @ResponseStatus(value = HttpStatus.OK)
    public BoletimUrnaUsuarioRetrievalDTO findBoletimUrnaUsuario(@Valid BoletimUrnaUsuarioIdDTO id) {
        return this.boletimUrnaUsuarioMapper.toBoletimUrnaUsuarioRetrievalDTO(this.boletimUrnaUsuarioService.findById(id));
    }

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
