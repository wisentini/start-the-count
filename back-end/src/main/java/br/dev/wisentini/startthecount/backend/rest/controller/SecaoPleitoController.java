package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoPleitoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoPleitoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoPleitoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.SecaoPleitoService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/secoes-pleitos")
@RequiredArgsConstructor
public class SecaoPleitoController {

    private final SecaoPleitoService secaoPleitoService;

    private final SecaoPleitoMapper secaoPleitoMapper;

    private final BoletimUrnaMapper boletimUrnaMapper;

    @GetMapping(params = {"numeroTSESecao", "numeroTSEZona", "siglaUF", "codigoTSEPleito"})
    @ResponseStatus(value = HttpStatus.OK)
    public SecaoPleitoRetrievalDTO findSecaoPleito(@Valid SecaoPleitoIdDTO id) {
        return this.secaoPleitoMapper.toSecaoPleitoRetrievalDTO(this.secaoPleitoService.findById(id));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<SecaoPleitoRetrievalDTO> findSecoesPleitos() {
        return this.secaoPleitoService
            .findAll()
            .stream()
            .map(this.secaoPleitoMapper::toSecaoPleitoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/boletins-urna")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<BoletimUrnaRetrievalDTO> findBoletinsUrna(@Valid SecaoPleitoIdDTO id) {
        return this.secaoPleitoService
            .findBoletinsUrna(id)
            .stream()
            .map(this.boletimUrnaMapper::toBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
