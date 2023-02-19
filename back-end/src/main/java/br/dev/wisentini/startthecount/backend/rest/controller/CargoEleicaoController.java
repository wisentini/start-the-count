package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.CargoEleicaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CargoEleicaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.CargoEleicaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.CargoEleicaoService;

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
@RequestMapping(value = "/cargos-eleicoes")
@RequiredArgsConstructor
@Tag(name = "cargos-eleicoes")
@SecurityRequirement(name = "bearerAuth")
public class CargoEleicaoController {

    private final CargoEleicaoService cargoEleicaoService;

    private final CargoEleicaoMapper cargoEleicaoMapper;

    @Operation(summary = "Encontra uma relação entre cargo e eleição.", description = "Encontra uma relação entre cargo e eleição.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public CargoEleicaoRetrievalDTO findCargoEleicao(
        @Parameter(description = "Os dados que identificam a relação entre cargo e eleição.")
        @Valid CargoEleicaoIdDTO id
    ) {
        return this.cargoEleicaoMapper.toCargoEleicaoRetrievalDTO(this.cargoEleicaoService.findById(id));
    }

    @Operation(summary = "Encontra todas as relações entre cargo e eleição.", description = "Encontra todas as relações entre cargo e eleição.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<CargoEleicaoRetrievalDTO> findCargosEleicoes() {
        return this.cargoEleicaoService
            .findAll()
            .stream()
            .map(this.cargoEleicaoMapper::toCargoEleicaoRetrievalDTO)
            .collect(Collectors.toList());
    }
}
