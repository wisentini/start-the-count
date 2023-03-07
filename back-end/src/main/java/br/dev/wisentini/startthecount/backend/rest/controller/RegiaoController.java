package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.RegiaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UFRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.RegiaoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.UFMapper;
import br.dev.wisentini.startthecount.backend.rest.service.RegiaoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/regioes")
@RequiredArgsConstructor
public class RegiaoController {

    private final RegiaoService regiaoService;

    private final RegiaoMapper regiaoMapper;

    private final UFMapper ufMapper;

    @GetMapping(value = "/{sigla}")
    @ResponseStatus(value = HttpStatus.OK)
    public RegiaoRetrievalDTO findRegiao(@PathVariable("sigla") String sigla) {
        return this.regiaoMapper.toRegiaoRetrievalDTO(this.regiaoService.findBySigla(sigla));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<RegiaoRetrievalDTO> findRegioes() {
        return this.regiaoService
            .findAll()
            .stream()
            .map(this.regiaoMapper::toRegiaoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{sigla}/ufs")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<UFRetrievalDTO> findUFs(@PathVariable("sigla") String sigla) {
        return this.regiaoService
            .findUFs(sigla)
            .stream()
            .map(this.ufMapper::toUFRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
