package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CargoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.EleicaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.CargoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.EleicaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.CargoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/cargos")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService cargoService;

    private final CargoMapper cargoMapper;

    private final EleicaoMapper eleicaoMapper;

    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public CargoRetrievalDTO findCargo(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.cargoMapper.toCargoRetrievalDTO(this.cargoService.findByCodigoTSE(codigoTSE));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<CargoRetrievalDTO> findCargos() {
        return this.cargoService
            .findAll()
            .stream()
            .map(this.cargoMapper::toCargoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{codigoTSE}/eleicoes")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<EleicaoRetrievalDTO> findEleicoes(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.cargoService
            .findEleicoes(codigoTSE)
            .stream()
            .map(this.eleicaoMapper::toEleicaoRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
