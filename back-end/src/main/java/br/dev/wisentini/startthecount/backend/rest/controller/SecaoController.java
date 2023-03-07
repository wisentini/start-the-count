package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PleitoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ProcessoEleitoralRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.PleitoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.ProcessoEleitoralMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.SecaoService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/secoes")
@RequiredArgsConstructor
public class SecaoController {

    private final SecaoService secaoService;

    private final SecaoMapper secaoMapper;

    private final PleitoMapper pleitoMapper;

    private final ProcessoEleitoralMapper processoEleitoralMapper;

    @GetMapping(params = {"numeroTSESecao", "numeroTSEZona", "siglaUF"})
    @ResponseStatus(value = HttpStatus.OK)
    public SecaoRetrievalDTO findSecao(@Valid SecaoIdDTO id) {
        return this.secaoMapper.toSecaoRetrievalDTO(this.secaoService.findById(id));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<SecaoRetrievalDTO> findSecoes() {
        return this.secaoService
            .findAll()
            .stream()
            .map(this.secaoMapper::toSecaoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/pleitos")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<PleitoRetrievalDTO> findPleitos(@Valid SecaoIdDTO id) {
        return this.secaoService
            .findPleitos(id)
            .stream()
            .map(this.pleitoMapper::toPleitoRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/processos-eleitorais")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<ProcessoEleitoralRetrievalDTO> findProcessosEleitorais(@Valid SecaoIdDTO id) {
        return this.secaoService
            .findProcessosEleitorais(id)
            .stream()
            .map(this.processoEleitoralMapper::toProcessoEleitoralRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/principais")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<SecaoRetrievalDTO> findSecoesPrincipais(@Valid SecaoIdDTO id) {
        return this.secaoService
            .findSecoesPrincipais(id)
            .stream()
            .map(this.secaoMapper::toSecaoRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/agregadas")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<SecaoRetrievalDTO> findSecoesAgregadas(@Valid SecaoIdDTO id) {
        return this.secaoService
            .findSecoesAgregadas(id)
            .stream()
            .map(this.secaoMapper::toSecaoRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
