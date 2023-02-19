package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.build.BoletimUrnaBuildDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.response.ApiResponse;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.QRCodeBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UsuarioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.UsuarioNaoAutenticadoException;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.QRCodeBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.UsuarioMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Usuario;
import br.dev.wisentini.startthecount.backend.rest.service.BoletimUrnaService;
import br.dev.wisentini.startthecount.backend.rest.service.BoletimUrnaUsuarioService;
import br.dev.wisentini.startthecount.backend.rest.service.QRCodeBoletimUrnaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/boletins-urna")
@RequiredArgsConstructor
@Tag(name = "boletins-urna")
@SecurityRequirement(name = "bearerAuth")
public class BoletimUrnaController {

    private final BoletimUrnaService boletimUrnaService;

    private final BoletimUrnaUsuarioService boletimUrnaUsuarioService;

    private final QRCodeBoletimUrnaService qrCodeBoletimUrnaService;

    private final BoletimUrnaMapper boletimUrnaMapper;

    private final UsuarioMapper usuarioMapper;

    private final QRCodeBoletimUrnaMapper qrCodeBoletimUrnaMapper;

    @Operation(
        summary = "Constrói um boletim de urna a partir de um ou mais payload(s) de QR code(s).",
        description = "Constrói um boletim de urna a partir de um ou mais payload(s) de QR code(s)."
    )
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public ApiResponse<String> buildBoletimUrna(
        @AuthenticationPrincipal Usuario usuario,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "O(s) payload(s) do(s) QR code(s) do boletim de urna.")
        @Valid @RequestBody BoletimUrnaBuildDTO boletimUrnaBuildDTO
    ) {
        if (Objects.isNull(usuario)) {
            throw new UsuarioNaoAutenticadoException("O usuário precisa estar autenticado para efetuar uma requisição de construção de boletim de urna.");
        }

        this.boletimUrnaUsuarioService.build(boletimUrnaBuildDTO, usuario);

        return new ApiResponse<>("O boletim de urna foi criado com sucesso!", HttpStatus.CREATED);
    }

    @Operation(summary = "Encontra um boletim de urna.", description = "Encontra um boletim de urna.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public BoletimUrnaRetrievalDTO findBoletimUrna(
        @Parameter(description = "Os dados que identificam o boletim de urna.")
        @Valid BoletimUrnaIdDTO id
    ) {
        return this.boletimUrnaMapper.toBoletimUrnaRetrievalDTO(this.boletimUrnaService.findById(id));
    }

    @Operation(summary = "Encontra todos os boletins de urna.", description = "Encontra todos os boletins de urna.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<BoletimUrnaRetrievalDTO> findBoletinsUrna() {
        return this.boletimUrnaService
            .findAll()
            .stream()
            .map(this.boletimUrnaMapper::toBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }

    @Operation(summary = "Encontra todos os usuários que coletaram um boletim de urna.", description = "Encontra todos os usuários que coletaram um boletim de urna.")
    @GetMapping(value = "/usuarios")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UsuarioRetrievalDTO> findUsuarios(
        @Parameter(description = "Os dados que identificam o boletim de urna.")
        @Valid BoletimUrnaIdDTO id
    ) {
        return this.boletimUrnaUsuarioService
            .findUsuariosByBoletimUrna(id)
            .stream()
            .map(this.usuarioMapper::toUsuarioRetrievalDTO)
            .collect(Collectors.toList());
    }

    @Operation(summary = "Encontra todos os QR codes de um boletim de urna.", description = "Encontra todos os QR codes de um boletim de urna.")
    @GetMapping(value = "/qr-codes")
    @ResponseStatus(value = HttpStatus.OK)
    public List<QRCodeBoletimUrnaRetrievalDTO> findQRCodes(
        @Parameter(description = "Os dados que identificam o boletim de urna.")
        @Valid BoletimUrnaIdDTO id
    ) {
        return this.qrCodeBoletimUrnaService
            .findByBoletimUrna(id)
            .stream()
            .map(this.qrCodeBoletimUrnaMapper::toQRCodeBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }
}
