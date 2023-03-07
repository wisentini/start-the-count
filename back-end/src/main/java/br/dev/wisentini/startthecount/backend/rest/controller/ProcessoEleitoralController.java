package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.AgregacaoSecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PleitoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ProcessoEleitoralRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.AgregacaoSecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.PleitoMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.ProcessoEleitoralMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.ProcessoEleitoralService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/processos-eleitorais")
@RequiredArgsConstructor
public class ProcessoEleitoralController {

    private final ProcessoEleitoralService processoEleitoralService;

    private final ProcessoEleitoralMapper processoEleitoralMapper;

    private final PleitoMapper pleitoMapper;

    private final SecaoMapper secaoMapper;

    private final AgregacaoSecaoMapper agregacaoSecaoMapper;

    @GetMapping(value = "/{codigoTSE}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProcessoEleitoralRetrievalDTO findProcessoEleitoral(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.processoEleitoralMapper
            .toProcessoEleitoralRetrievalDTO(this.processoEleitoralService.findByCodigoTSE(codigoTSE));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProcessoEleitoralRetrievalDTO> findProcessosEleitorais() {
        return this.processoEleitoralService
            .findAll()
            .stream()
            .map(this.processoEleitoralMapper::toProcessoEleitoralRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{codigoTSE}/secoes")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<SecaoRetrievalDTO> findSecoes(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.processoEleitoralService
            .findSecoes(codigoTSE)
            .stream()
            .map(this.secaoMapper::toSecaoRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/{codigoTSE}/agregacoes-secao")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<AgregacaoSecaoRetrievalDTO> findAgregacoesSecao(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.processoEleitoralService
            .findSecoesAgregadas(codigoTSE)
            .stream()
            .map(this.agregacaoSecaoMapper::toAgregacaoSecaoRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/{codigoTSE}/pleitos")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<PleitoRetrievalDTO> findPleitos(@PathVariable("codigoTSE") Integer codigoTSE) {
        return this.processoEleitoralService
            .findPleitos(codigoTSE)
            .stream()
            .map(this.pleitoMapper::toPleitoRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
