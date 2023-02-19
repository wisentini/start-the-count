package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.service.ApuracaoVotosCandidaturaBoletimUrnaService;
import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosCandidaturaBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosCandidaturaBoletimUrnaMapper;

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
@RequestMapping(value = "/apuracoes-votos-candidaturas-boletins-urna")
@RequiredArgsConstructor
@Tag(name = "apuracoes-votos-candidaturas-boletins-urna")
@SecurityRequirement(name = "bearerAuth")
public class ApuracaoVotosCandidaturaBoletimUrnaController {

    private final ApuracaoVotosCandidaturaBoletimUrnaService apuracaoVotosCandidaturaBoletimUrnaService;

    private final ApuracaoVotosCandidaturaBoletimUrnaMapper apuracaoVotosCandidaturaBoletimUrnaMapper;

    @Operation(summary = "Encontra uma apuração de votos de candidatura por boletim de urna.", description = "Encontra uma apuração de votos de candidatura por boletim de urna.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO findApuracaoVotosCandidaturaBoletimUrna(
        @Parameter(description = "Os dados que identificam a apuração de votos de candidatura.")
        @Valid ApuracaoVotosCandidaturaBoletimUrnaIdDTO id
    ) {
        return this.apuracaoVotosCandidaturaBoletimUrnaMapper
            .toApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO(
                this.apuracaoVotosCandidaturaBoletimUrnaService.findById(id)
            );
    }

    @Operation(summary = "Encontra todas as apurações de votos de candidaturas por boletim de urna.", description = "Encontra todas as apurações de votos de candidaturas por boletim de urna.")
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
