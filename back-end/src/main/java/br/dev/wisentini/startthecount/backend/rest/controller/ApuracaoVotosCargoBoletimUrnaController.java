package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosCargoBoletimUrnaService;
import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosCargoBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCargoBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosCargoBoletimUrnaMapper;

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
@RequestMapping(value = "/apuracoes-votos-cargos-boletins-urna")
@RequiredArgsConstructor
@Tag(name = "apuracoes-votos-cargos-boletins-urna")
@SecurityRequirement(name = "bearerAuth")
public class ApuracaoVotosCargoBoletimUrnaController {

    private final ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService;

    private final ApuracaoVotosCargoBoletimUrnaMapper apuracaoVotosCargoBoletimUrnaMapper;

    @Operation(summary = "Encontra uma apuração de votos de cargo por boletim de urna.", description = "Encontra uma apuração de votos de cargo por boletim de urna.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public ApuracaoVotosCargoBoletimUrnaRetrievalDTO findApuracaoVotosCargoBoletimUrna(
        @Parameter(description = "Os dados que identificam a apuração de votos de cargo.")
        @Valid ApuracaoVotosCargoBoletimUrnaIdDTO id
    ) {
        return this.apuracaoVotosCargoBoletimUrnaMapper
            .toApuracaoVotosCargoBoletimUrnaRetrievalDTO(
                this.apuracaoVotosCargoBoletimUrnaService.findById(id)
            );
    }

    @Operation(summary = "Encontra todas as apurações de votos de cargos por boletim de urna.", description = "Encontra todas as apurações de votos de cargos por boletim de urna.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApuracaoVotosCargoBoletimUrnaRetrievalDTO> findApuracoesVotosCargoBoletimUrna() {
        return this.apuracaoVotosCargoBoletimUrnaService
            .findAll()
            .stream()
            .map(this.apuracaoVotosCargoBoletimUrnaMapper::toApuracaoVotosCargoBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }
}
