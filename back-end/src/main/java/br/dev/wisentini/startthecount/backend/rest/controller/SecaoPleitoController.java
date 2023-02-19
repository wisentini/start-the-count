package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoPleitoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoPleitoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoPleitoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.SecaoPleitoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/secoes-pleitos")
@RequiredArgsConstructor
@Tag(name = "secoes-pleitos")
@SecurityRequirement(name = "bearerAuth")
public class SecaoPleitoController {

    private final SecaoPleitoService secaoPleitoService;

    private final SecaoPleitoMapper secaoPleitoMapper;

    @Operation(summary = "Encontra uma relação entre seção e pleito.", description = "Encontra uma relação entre seção e pleito.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public SecaoPleitoRetrievalDTO findSecaoPleito(
        @Parameter(description = "Os dados que identificam a relação entre seção e pleito.")
        @Valid SecaoPleitoIdDTO id
    ) {
        return this.secaoPleitoMapper.toSecaoPleitoRetrievalDTO(this.secaoPleitoService.findById(id));
    }

    @Operation(summary = "Encontra todas as relações entre seção e pleito.", description = "Encontra todas as relações entre seção e pleito.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<SecaoPleitoRetrievalDTO> findSecoesPleitos() {
        return this.secaoPleitoService
            .findAll()
            .stream()
            .map(this.secaoPleitoMapper::toSecaoPleitoRetrievalDTO)
            .collect(Collectors.toList());
    }
}
