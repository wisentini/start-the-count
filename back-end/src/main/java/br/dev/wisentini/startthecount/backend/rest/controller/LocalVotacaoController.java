package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.LocalVotacaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.LocalVotacaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.LocalVotacaoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.LocalVotacaoService;

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
@RequestMapping(value = "/api/locais-votacao")
@RequiredArgsConstructor
public class LocalVotacaoController {

    private final LocalVotacaoService localVotacaoService;

    private final LocalVotacaoMapper localVotacaoMapper;

    private final SecaoMapper secaoMapper;

    @GetMapping(params = {"numeroTSELocalVotacao", "numeroTSEZona", "siglaUF"})
    @ResponseStatus(value = HttpStatus.OK)
    public LocalVotacaoRetrievalDTO findLocalVotacao(@Valid LocalVotacaoIdDTO id) {
        return this.localVotacaoMapper.toLocalVotacaoRetrievalDTO(this.localVotacaoService.findById(id));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<LocalVotacaoRetrievalDTO> findLocaisVotacao() {
        return this.localVotacaoService
            .findAll()
            .stream()
            .map(this.localVotacaoMapper::toLocalVotacaoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/secoes")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<SecaoRetrievalDTO> findSecoes(@Valid LocalVotacaoIdDTO id) {
        return this.localVotacaoService
            .findSecoes(id)
            .stream()
            .map(this.secaoMapper::toSecaoRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
