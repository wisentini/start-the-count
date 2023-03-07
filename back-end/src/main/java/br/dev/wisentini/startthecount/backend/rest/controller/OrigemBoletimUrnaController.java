package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.OrigemBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.OrigemBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.service.OrigemBoletimUrnaService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/origens-boletim-urna")
@RequiredArgsConstructor
public class OrigemBoletimUrnaController {

    private final OrigemBoletimUrnaService origemBoletimUrnaService;

    private final OrigemBoletimUrnaMapper origemBoletimUrnaMapper;

    private final BoletimUrnaMapper boletimUrnaMapper;

    @GetMapping(value = "/{nomeAbreviado}")
    @ResponseStatus(value = HttpStatus.OK)
    public OrigemBoletimUrnaRetrievalDTO findOrigemBoletimUrna(@PathVariable("nomeAbreviado") String nomeAbreviado) {
        return this.origemBoletimUrnaMapper.toOrigemBoletimUrnaRetrievalDTO(
            this.origemBoletimUrnaService.findByNomeAbreviado(nomeAbreviado)
        );
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<OrigemBoletimUrnaRetrievalDTO> findOrigensBoletimUrna() {
        return this.origemBoletimUrnaService
            .findAll()
            .stream()
            .map(this.origemBoletimUrnaMapper::toOrigemBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{nomeAbreviado}/boletins-urna")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<BoletimUrnaRetrievalDTO> findBoletinsUrna(@PathVariable("nomeAbreviado") String nomeAbreviado) {
        return this.origemBoletimUrnaService
            .findBoletinsUrna(nomeAbreviado)
            .stream()
            .map(this.boletimUrnaMapper::toBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
