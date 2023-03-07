package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosCandidaturaBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosCandidaturaBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosCandidaturaBoletimUrnaService;

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
@RequestMapping(value = "/api/apuracoes-votos-candidaturas-boletim-urna")
@RequiredArgsConstructor
public class ApuracaoVotosCandidaturaBoletimUrnaController {

    private final ApuracaoVotosCandidaturaBoletimUrnaService apuracaoVotosCandidaturaBoletimUrnaService;

    private final ApuracaoVotosCandidaturaBoletimUrnaMapper apuracaoVotosCandidaturaBoletimUrnaMapper;

    @GetMapping(params = {"numeroTSECandidato", "codigoTSECargo", "codigoTSEEleicao", "numeroTSESecao", "numeroTSEZona", "siglaUF", "codigoTSEPleito"})
    @ResponseStatus(value = HttpStatus.OK)
    public ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO findApuracaoVotosCandidaturaBoletimUrna(
        @Valid ApuracaoVotosCandidaturaBoletimUrnaIdDTO id
    ) {
        return this.apuracaoVotosCandidaturaBoletimUrnaMapper
            .toApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO(
                this.apuracaoVotosCandidaturaBoletimUrnaService.findById(id)
            );
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO> findApuracoesVotosCandidaturaBoletimUrna() {
        return this.apuracaoVotosCandidaturaBoletimUrnaService
            .findAll()
            .stream()
            .map(this.apuracaoVotosCandidaturaBoletimUrnaMapper::toApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }
}
