package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.MunicipioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UFRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ZonaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.MunicipioMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.UFMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.ZonaMapper;
import br.dev.wisentini.startthecount.backend.rest.service.UFService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/ufs")
@RequiredArgsConstructor
public class UFController {

    private final UFService ufService;

    private final UFMapper ufMapper;

    private final ZonaMapper zonaMapper;

    private final MunicipioMapper municipioMapper;

    @GetMapping(value = "/{sigla}")
    @ResponseStatus(value = HttpStatus.OK)
    public UFRetrievalDTO findUF(@PathVariable("sigla") String sigla) {
        return this.ufMapper.toUFRetrievalDTO(this.ufService.findBySigla(sigla));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<UFRetrievalDTO> findUFs() {
        return this.ufService
            .findAll()
            .stream()
            .map(this.ufMapper::toUFRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{sigla}/zonas")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<ZonaRetrievalDTO> findZonas(@PathVariable("sigla") String sigla) {
        return this.ufService
            .findZonas(sigla)
            .stream()
            .map(this.zonaMapper::toZonaRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/{sigla}/municipios")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<MunicipioRetrievalDTO> findMunicipios(@PathVariable("sigla") String sigla) {
        return this.ufService
            .findMunicipios(sigla)
            .stream()
            .map(this.municipioMapper::toMunicipioRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
