package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.UrnaEletronica;
import br.dev.wisentini.startthecount.backend.rest.service.BoletimUrnaService;
import br.dev.wisentini.startthecount.backend.rest.service.UrnaEletronicaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/urnas-eletronicas")
@RequiredArgsConstructor
@Tag(name = "urnas-eletronicas")
@SecurityRequirement(name = "bearerAuth")
public class UrnaEletronicaController {

    private final UrnaEletronicaService urnaEletronicaService;

    private final BoletimUrnaService boletimUrnaService;

    private final BoletimUrnaMapper boletimUrnaMapper;

    @Operation(summary = "Encontra uma urna eletrônica.", description = "Encontra uma urna eletrônica.")
    @GetMapping(value = "/{numeroSerie}")
    @ResponseStatus(value = HttpStatus.OK)
    public UrnaEletronica findUrnaEletronica(
        @Parameter(description = "O número de série da urna eletrônica.")
        @PathVariable("numeroSerie") int numeroSerie
    ) {
        return this.urnaEletronicaService.findByNumeroSerie(numeroSerie);
    }

    @Operation(summary = "Encontra todas as urnas eletrônicas.", description = "Encontra todas as urnas eletrônicas.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<UrnaEletronica> findUrnasEletronicas() {
        return this.urnaEletronicaService.findAll();
    }

    @Operation(summary = "Encontra todos os boletins de urna uma urna eletrônica.", description = "Encontra todos os boletins de urna de uma urna eletrônica.")
    @GetMapping(value = "/{numeroSerie}/boletins-urna")
    @ResponseStatus(value = HttpStatus.OK)
    public List<BoletimUrnaRetrievalDTO> findBoletinsUrna(
        @Parameter(description = "O número de série da urna eletrônica.")
        @PathVariable("numeroSerie") int numeroSerie
    ) {
        return this.boletimUrnaService
            .findByUrnaEletronica(numeroSerie)
            .stream()
            .map(this.boletimUrnaMapper::toBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }
}
