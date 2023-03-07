package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosPartidoBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CandidaturaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PartidoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosPartidoBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.CandidaturaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.PartidoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.PartidoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/partidos")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;

    private final PartidoMapper partidoMapper;

    private final CandidaturaMapper candidaturaMapper;

    private final ApuracaoVotosPartidoBoletimUrnaMapper apuracaoVotosPartidoBoletimUrnaMapper;

    @GetMapping(value = "/{numeroTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public PartidoRetrievalDTO findPartido(@PathVariable("numeroTSE") Integer numeroTSE) {
        return this.partidoMapper.toPartidoRetrievalDTO(this.partidoService.findByNumeroTSE(numeroTSE));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PartidoRetrievalDTO> findPartidos() {
        return this.partidoService
            .findAll()
            .stream()
            .map(this.partidoMapper::toPartidoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{numeroTSE}/candidaturas")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<CandidaturaRetrievalDTO> findCandidaturas(@PathVariable("numeroTSE") Integer numeroTSE) {
        return this.partidoService
            .findCandidaturas(numeroTSE)
            .stream()
            .map(this.candidaturaMapper::toCandidaturaRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/{numeroTSE}/apuracoes-votos-boletim-urna")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<ApuracaoVotosPartidoBoletimUrnaRetrievalDTO> findApuracoesVotosBoletimUrna(
        @PathVariable("numeroTSE") Integer numeroTSE
    ) {
        return this.partidoService
            .findApuracoesVotosBoletimUrna(numeroTSE)
            .stream()
            .map(this.apuracaoVotosPartidoBoletimUrnaMapper::toApuracaoVotosPartidoBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
