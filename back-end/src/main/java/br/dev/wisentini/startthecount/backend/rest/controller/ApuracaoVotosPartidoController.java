package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosPartidoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosPartidoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/apuracoes-votos-partidos")
@RequiredArgsConstructor
public class ApuracaoVotosPartidoController {

    private final ApuracaoVotosPartidoService apuracaoVotosPartidoService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApuracaoVotosPartidoRetrievalDTO> findApuracoesVotosPartidos() {
        return this.apuracaoVotosPartidoService.findAll();
    }
}
