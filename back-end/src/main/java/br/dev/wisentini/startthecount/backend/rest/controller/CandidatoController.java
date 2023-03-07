package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CandidatoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CandidaturaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.CandidatoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.CandidaturaMapper;
import br.dev.wisentini.startthecount.backend.rest.service.CandidatoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/candidatos")
@RequiredArgsConstructor
public class CandidatoController {

    private final CandidatoService candidatoService;

    private final CandidatoMapper candidatoMapper;

    private final CandidaturaMapper candidaturaMapper;

    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public CandidatoRetrievalDTO findCandidato(@PathVariable("codigoTSE") String codigoTSE) {
        return this.candidatoMapper.toCandidatoRetrievalDTO(this.candidatoService.findByCodigoTSE(codigoTSE));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<CandidatoRetrievalDTO> findCandidatos() {
        return this.candidatoService
            .findAll()
            .stream()
            .map(this.candidatoMapper::toCandidatoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{codigoTSE}/candidaturas")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<CandidaturaRetrievalDTO> findCandidaturas(@PathVariable("codigoTSE") String codigoTSE) {
        return this.candidatoService
            .findCandidaturas(codigoTSE)
            .stream()
            .map(this.candidaturaMapper::toCandidaturaRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
