package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.QRCodeBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.QRCodeBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.QRCodeBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.service.QRCodeBoletimUrnaService;

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
@RequestMapping(value = "/api/qr-codes-boletim-urna")
@RequiredArgsConstructor
public class QRCodeBoletimUrnaController {

    private final QRCodeBoletimUrnaService qrCodeBoletimUrnaService;

    private final QRCodeBoletimUrnaMapper qrCodeBoletimUrnaMapper;

    @GetMapping(params = {"indice", "numeroTSESecao", "numeroTSEZona", "siglaUF", "codigoTSEPleito"})
    @ResponseStatus(value = HttpStatus.OK)
    public QRCodeBoletimUrnaRetrievalDTO findQRCodeBoletimUrna(@Valid QRCodeBoletimUrnaIdDTO id) {
        return this.qrCodeBoletimUrnaMapper.toQRCodeBoletimUrnaRetrievalDTO(this.qrCodeBoletimUrnaService.findById(id));
    }

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
