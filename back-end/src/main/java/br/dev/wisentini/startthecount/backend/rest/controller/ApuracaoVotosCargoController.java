package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCargoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosCargoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/apuracoes-votos-cargos")
@RequiredArgsConstructor
public class ApuracaoVotosCargoController {

    private final ApuracaoVotosCargoService apuracaoVotosCargoService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApuracaoVotosCargoRetrievalDTO> findApuracoesVotosCargos() {
        return this.apuracaoVotosCargoService.findAll();
    }
}
