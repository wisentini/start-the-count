package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.AgregacaoSecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.AgregacaoSecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.AgregacaoSecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.AgregacaoSecaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@RequestMapping(value = "/agregacoes-secao")
@RequiredArgsConstructor
@Tag(name = "agregacoes-secao")
@SecurityRequirement(name = "bearerAuth")
public class AgregacaoSecaoController {

    private final AgregacaoSecaoService agregacaoSecaoService;

    private final AgregacaoSecaoMapper agregacaoSecaoMapper;

    @Operation(summary = "Encontra uma agregação de seção.", description = "Encontra uma agregação de seção.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public AgregacaoSecaoRetrievalDTO findAgregacaoSecao(
        @Parameter(description = "Os dados que identificam a agregação de seção.")
        @Valid AgregacaoSecaoIdDTO id
    ) {
        return this.agregacaoSecaoMapper.toAgregacaoSecaoRetrievalDTO(this.agregacaoSecaoService.findById(id));
    }

    @Operation(summary = "Encontra todas as agregações de seção.", description = "Encontra todas as agregações de seção.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<AgregacaoSecaoRetrievalDTO> findAgregacoesSecao() {
        return this.agregacaoSecaoService
            .findAll()
            .stream()
            .map(this.agregacaoSecaoMapper::toAgregacaoSecaoRetrievalDTO)
            .collect(Collectors.toList());
    }
}
