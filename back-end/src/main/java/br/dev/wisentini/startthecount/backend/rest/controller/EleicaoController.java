package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CargoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.EleicaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.CargoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.EleicaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.EleicaoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/eleicoes")
@RequiredArgsConstructor
public class EleicaoController {

    private final EleicaoService eleicaoService;

    private final EleicaoMapper eleicaoMapper;

    private final CargoMapper cargoMapper;

    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public EleicaoRetrievalDTO findEleicao(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.eleicaoMapper.toEleicaoRetrievalDTO(this.eleicaoService.findByCodigoTSE(codigoTSE));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<EleicaoRetrievalDTO> findEleicoes() {
        return this.eleicaoService
            .findAll()
            .stream()
            .map(this.eleicaoMapper::toEleicaoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{codigoTSE}/cargos")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<CargoRetrievalDTO> findCargos(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.eleicaoService
            .findCargos(codigoTSE)
            .stream()
            .map(this.cargoMapper::toCargoRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
