package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosPartidoBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosPartidoBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosPartidoBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosPartidoBoletimUrnaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/apuracoes-votos-partidos-boletins-urna")
@RequiredArgsConstructor
@Tag(name = "apuracoes-votos-partidos-boletins-urna")
@SecurityRequirement(name = "bearerAuth")
public class ApuracaoVotosPartidoBoletimUrnaController {

    private final ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService;

    private final ApuracaoVotosPartidoBoletimUrnaMapper apuracaoVotosPartidoBoletimUrnaMapper;

    @Operation(summary = "Encontra uma apuração de votos de partido por boletim de urna.", description = "Encontra uma apuração de votos de partido por boletim de urna.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public ApuracaoVotosPartidoBoletimUrnaRetrievalDTO findApuracaoVotosPartidoBoletimUrna(
        @Parameter(description = "Os dados que identificam a apuração de votos de partido.")
        @Valid ApuracaoVotosPartidoBoletimUrnaIdDTO id
    ) {
        return this.apuracaoVotosPartidoBoletimUrnaMapper
            .toApuracaoVotosPartidoBoletimUrnaRetrievalDTO(
                this.apuracaoVotosPartidoBoletimUrnaService.findById(id)
            );
    }

    @Operation(summary = "Encontra todas as apurações de votos de partidos por boletim de urna.", description = "Encontra todas as apurações de votos de partidos por boletim de urna.")
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
