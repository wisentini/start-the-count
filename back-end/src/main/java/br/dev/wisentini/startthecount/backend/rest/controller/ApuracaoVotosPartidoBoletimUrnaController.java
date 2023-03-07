package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosPartidoBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosPartidoBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosPartidoBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosPartidoBoletimUrnaService;

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
@RequestMapping(value = "/api/apuracoes-votos-partidos-boletim-urna")
@RequiredArgsConstructor
public class ApuracaoVotosPartidoBoletimUrnaController {

    private final ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService;

    private final ApuracaoVotosPartidoBoletimUrnaMapper apuracaoVotosPartidoBoletimUrnaMapper;

    @GetMapping(params = {"numeroTSEPartido", "codigoTSECargo", "codigoTSEEleicao", "numeroTSESecao", "numeroTSEZona", "siglaUF", "codigoTSEPleito"})
    @ResponseStatus(value = HttpStatus.OK)
    public ApuracaoVotosPartidoBoletimUrnaRetrievalDTO findApuracaoVotosPartidoBoletimUrna(
        @Valid ApuracaoVotosPartidoBoletimUrnaIdDTO id
    ) {
        return this.apuracaoVotosPartidoBoletimUrnaMapper
            .toApuracaoVotosPartidoBoletimUrnaRetrievalDTO(
                this.apuracaoVotosPartidoBoletimUrnaService.findById(id)
            );
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApuracaoVotosPartidoBoletimUrnaRetrievalDTO> findApuracoesVotosPartidoBoletimUrna() {
        return this.apuracaoVotosPartidoBoletimUrnaService
            .findAll()
            .stream()
            .map(this.apuracaoVotosPartidoBoletimUrnaMapper::toApuracaoVotosPartidoBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }
}
