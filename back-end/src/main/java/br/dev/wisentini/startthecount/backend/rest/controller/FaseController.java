package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.FaseRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.FaseMapper;
import br.dev.wisentini.startthecount.backend.rest.service.FaseService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/fases")
@RequiredArgsConstructor
public class FaseController {

    private final FaseService faseService;

    private final FaseMapper faseMapper;

    private final BoletimUrnaMapper boletimUrnaMapper;

    @GetMapping(value = "/{nome}")
    @ResponseStatus(value = HttpStatus.OK)
    public FaseRetrievalDTO findFase(@PathVariable("nome") String nome) {
        return this.faseMapper.toFaseRetrievalDTO(this.faseService.findByNome(nome));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<FaseRetrievalDTO> findFases() {
        return this.faseService
            .findAll()
            .stream()
            .map(this.faseMapper::toFaseRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{nome}/boletins-urna")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<BoletimUrnaRetrievalDTO> findBoletinsUrna(@PathVariable("nome") String nome) {
        return this.faseService
            .findBoletinsUrna(nome)
            .stream()
            .map(this.boletimUrnaMapper::toBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
