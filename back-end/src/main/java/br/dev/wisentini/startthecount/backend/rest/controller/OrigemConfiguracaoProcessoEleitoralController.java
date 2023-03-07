package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.OrigemConfiguracaoProcessoEleitoralRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ProcessoEleitoralRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.OrigemConfiguracaoProcessoEleitoralMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.ProcessoEleitoralMapper;
import br.dev.wisentini.startthecount.backend.rest.service.OrigemConfiguracaoProcessoEleitoralService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/origens-configuracao-processo-eleitoral")
@RequiredArgsConstructor
public class OrigemConfiguracaoProcessoEleitoralController {

    private final OrigemConfiguracaoProcessoEleitoralService origemConfiguracaoProcessoEleitoralService;

    private final OrigemConfiguracaoProcessoEleitoralMapper origemConfiguracaoProcessoEleitoralMapper;

    private final ProcessoEleitoralMapper processoEleitoralMapper;

    @GetMapping(value = "/{nomeAbreviado}")
    @ResponseStatus(value = HttpStatus.OK)
    public OrigemConfiguracaoProcessoEleitoralRetrievalDTO findOrigemConfiguracaoProcessoEleitoral(
        @PathVariable("nomeAbreviado") String nomeAbreviado
    ) {
        return this.origemConfiguracaoProcessoEleitoralMapper.toOrigemConfiguracaoProcessoEleitoralRetrievalDTO(
            this.origemConfiguracaoProcessoEleitoralService.findByNomeAbreviado(nomeAbreviado)
        );
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<OrigemConfiguracaoProcessoEleitoralRetrievalDTO> findOrigensConfiguracaoProcessoEleitoral() {
        return this.origemConfiguracaoProcessoEleitoralService
            .findAll()
            .stream()
            .map(this.origemConfiguracaoProcessoEleitoralMapper::toOrigemConfiguracaoProcessoEleitoralRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{nomeAbreviado}/processos-eleitorais")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<ProcessoEleitoralRetrievalDTO> findProcessosEleitorais(@PathVariable("nomeAbreviado") String nomeAbreviado) {
        return this.origemConfiguracaoProcessoEleitoralService
            .findProcessosEleitorais(nomeAbreviado)
            .stream()
            .map(this.processoEleitoralMapper::toProcessoEleitoralRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
