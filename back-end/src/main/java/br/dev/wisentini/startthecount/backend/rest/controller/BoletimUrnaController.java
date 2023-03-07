package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.build.BoletimUrnaBuildDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.response.ApiResponse;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.*;
import br.dev.wisentini.startthecount.backend.rest.exception.UsuarioNaoAutenticadoException;
import br.dev.wisentini.startthecount.backend.rest.mapper.*;
import br.dev.wisentini.startthecount.backend.rest.model.Usuario;
import br.dev.wisentini.startthecount.backend.rest.service.BoletimUrnaService;
import br.dev.wisentini.startthecount.backend.rest.service.BoletimUrnaUsuarioService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/boletins-urna")
@RequiredArgsConstructor
public class BoletimUrnaController {

    private final BoletimUrnaService boletimUrnaService;

    private final BoletimUrnaUsuarioService boletimUrnaUsuarioService;

    private final BoletimUrnaMapper boletimUrnaMapper;

    private final UsuarioMapper usuarioMapper;

    private final QRCodeBoletimUrnaMapper qrCodeBoletimUrnaMapper;

    private final ApuracaoVotosCandidaturaBoletimUrnaMapper apuracaoVotosCandidaturaBoletimUrnaMapper;

    private final ApuracaoVotosCargoBoletimUrnaMapper apuracaoVotosCargoBoletimUrnaMapper;

    private final ApuracaoVotosPartidoBoletimUrnaMapper apuracaoVotosPartidoBoletimUrnaMapper;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public ApiResponse<String> buildBoletimUrna(
        @AuthenticationPrincipal Usuario usuario,
        @Valid @RequestBody BoletimUrnaBuildDTO boletimUrnaBuildDTO
    ) {
        if (Objects.isNull(usuario)) {
            throw new UsuarioNaoAutenticadoException("O usuário precisa estar autenticado para efetuar uma requisição de construção de boletim de urna.");
        }

        this.boletimUrnaUsuarioService.build(boletimUrnaBuildDTO, usuario);

        return new ApiResponse<>("O boletim de urna foi criado com sucesso!", HttpStatus.CREATED);
    }

    @GetMapping(params = {"numeroTSESecao", "numeroTSEZona", "siglaUF", "codigoTSEPleito"})
    @ResponseStatus(value = HttpStatus.OK)
    public BoletimUrnaRetrievalDTO findBoletimUrna(@Valid BoletimUrnaIdDTO id) {
        return this.boletimUrnaMapper.toBoletimUrnaRetrievalDTO(this.boletimUrnaService.findById(id));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<BoletimUrnaRetrievalDTO> findBoletinsUrna() {
        return this.boletimUrnaService
            .findAll()
            .stream()
            .map(this.boletimUrnaMapper::toBoletimUrnaRetrievalDTO)
            .collect(Collectors.toList());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@Valid BoletimUrnaIdDTO id) {
        this.boletimUrnaService.deleteById(id);
    }

    @GetMapping(value = "/usuarios")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<UsuarioRetrievalDTO> findUsuarios(@Valid BoletimUrnaIdDTO id) {
        return this.boletimUrnaService
            .findUsuarios(id)
            .stream()
            .map(this.usuarioMapper::toUsuarioRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/qr-codes")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<QRCodeBoletimUrnaRetrievalDTO> findQRCodes(@Valid BoletimUrnaIdDTO id) {
        return this.boletimUrnaService
            .findQRCodes(id)
            .stream()
            .map(this.qrCodeBoletimUrnaMapper::toQRCodeBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/apuracoes-votos-candidaturas")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO> findApuracoesVotosCandidaturas(@Valid BoletimUrnaIdDTO id) {
        return this.boletimUrnaService
            .findApuracoesVotosCandidaturas(id)
            .stream()
            .map(this.apuracaoVotosCandidaturaBoletimUrnaMapper::toApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/apuracoes-votos-cargos")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<ApuracaoVotosCargoBoletimUrnaRetrievalDTO> findApuracoesVotosCargos(@Valid BoletimUrnaIdDTO id) {
        return this.boletimUrnaService
            .findApuracoesVotosCargos(id)
            .stream()
            .map(this.apuracaoVotosCargoBoletimUrnaMapper::toApuracaoVotosCargoBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/apuracoes-votos-partidos")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<ApuracaoVotosPartidoBoletimUrnaRetrievalDTO> findApuracoesVotosPartidos(@Valid BoletimUrnaIdDTO id) {
        return this.boletimUrnaService
            .findApuracoesVotosPartidos(id)
            .stream()
            .map(this.apuracaoVotosPartidoBoletimUrnaMapper::toApuracaoVotosPartidoBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
