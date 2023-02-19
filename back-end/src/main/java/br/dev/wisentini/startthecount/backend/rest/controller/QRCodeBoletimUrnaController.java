package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.QRCodeBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.QRCodeBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.QRCodeBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.service.QRCodeBoletimUrnaService;

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
@RequestMapping(value = "/qr-codes-boletim-urna")
@RequiredArgsConstructor
@Tag(name = "qr-codes-boletim-urna")
@SecurityRequirement(name = "bearerAuth")
public class QRCodeBoletimUrnaController {

    private final QRCodeBoletimUrnaService qrCodeBoletimUrnaService;

    private final QRCodeBoletimUrnaMapper qrCodeBoletimUrnaMapper;

    @Operation(summary = "Encontra um QR code de boletim de urna.", description = "Encontra um QR code de boletim de urna.")
    @GetMapping(value = "/id")
    @ResponseStatus(value = HttpStatus.OK)
    public QRCodeBoletimUrnaRetrievalDTO findQRCodeBoletimUrna(
        @Parameter(description = "Os dados que identificam um QR code de boletim de urna.")
        @Valid QRCodeBoletimUrnaIdDTO id
    ) {
        return this.qrCodeBoletimUrnaMapper.toQRCodeBoletimUrnaRetrievalDTO(this.qrCodeBoletimUrnaService.findById(id));
    }

    @Operation(summary = "Encontra todos os QR codes de boletim de urna.", description = "Encontra todos os QR codes de boletim de urna.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<QRCodeBoletimUrnaRetrievalDTO> findQRCodesBoletimUrna() {
        return this.qrCodeBoletimUrnaService
            .findAll()
            .stream()
            .map(this.qrCodeBoletimUrnaMapper::toQRCodeBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }
}
