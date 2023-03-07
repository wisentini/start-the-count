package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CargoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.TipoCargoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.CargoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.TipoCargoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.TipoCargoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/tipos-cargo")
@RequiredArgsConstructor
public class TipoCargoController {

    private final TipoCargoService tipoCargoService;

    private final TipoCargoMapper tipoCargoMapper;

    private final CargoMapper cargoMapper;

    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public TipoCargoRetrievalDTO findTipoCargo(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.tipoCargoMapper.toTipoCargoRetrievalDTO(this.tipoCargoService.findByCodigoTSE(codigoTSE));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<TipoCargoRetrievalDTO> findTiposCargo() {
        return this.tipoCargoService
            .findAll()
            .stream()
            .map(this.tipoCargoMapper::toTipoCargoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{codigoTSE}/cargos")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<CargoRetrievalDTO> findCargos(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.tipoCargoService
            .findCargos(codigoTSE)
            .stream()
            .map(this.cargoMapper::toCargoRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
