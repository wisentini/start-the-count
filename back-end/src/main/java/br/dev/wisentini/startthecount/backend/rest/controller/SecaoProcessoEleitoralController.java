package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoProcessoEleitoralIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoProcessoEleitoralRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoProcessoEleitoralMapper;
import br.dev.wisentini.startthecount.backend.rest.service.SecaoProcessoEleitoralService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/secoes-processos-eleitorais")
@RequiredArgsConstructor
public class SecaoProcessoEleitoralController {

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    private final SecaoProcessoEleitoralMapper secaoProcessoEleitoralMapper;

    @GetMapping(params = {"numeroTSESecao", "numeroTSEZona", "siglaUF", "codigoTSEProcessoEleitoral"})
    @ResponseStatus(value = HttpStatus.OK)
    public SecaoProcessoEleitoralRetrievalDTO findSecaoProcessoEleitoral(@Valid SecaoProcessoEleitoralIdDTO id) {
        return this.secaoProcessoEleitoralMapper
            .toSecaoProcessoEleitoralRetrievalDTO(this.secaoProcessoEleitoralService.findById(id));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<SecaoProcessoEleitoralRetrievalDTO> findSecoesProcessosEleitorais() {
        return this.secaoProcessoEleitoralService
            .findAll()
            .stream()
            .map(this.secaoProcessoEleitoralMapper::toSecaoProcessoEleitoralRetrievalDTO)
            .collect(Collectors.toList());
    }
}
