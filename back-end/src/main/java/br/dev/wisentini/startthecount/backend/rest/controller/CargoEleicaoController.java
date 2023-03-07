package br.dev.wisentini.startthecount.backend.rest.controller;

import br.dev.wisentini.startthecount.backend.rest.dto.id.CargoEleicaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCargoBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosPartidoBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CandidaturaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CargoEleicaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosCargoBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosPartidoBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.CandidaturaMapper;
import br.dev.wisentini.startthecount.backend.rest.mapper.CargoEleicaoMapper;
import br.dev.wisentini.startthecount.backend.rest.service.CargoEleicaoService;

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
@RequestMapping(value = "/api/cargos-eleicoes")
@RequiredArgsConstructor
public class CargoEleicaoController {

    private final CargoEleicaoService cargoEleicaoService;

    private final CargoEleicaoMapper cargoEleicaoMapper;

    private final CandidaturaMapper candidaturaMapper;

    private final ApuracaoVotosCargoBoletimUrnaMapper apuracaoVotosCargoBoletimUrnaMapper;

    private final ApuracaoVotosPartidoBoletimUrnaMapper apuracaoVotosPartidoBoletimUrnaMapper;

    @GetMapping(params = {"codigoTSECargo", "codigoTSEEleicao"})
    @ResponseStatus(value = HttpStatus.OK)
    public CargoEleicaoRetrievalDTO findCargoEleicao(@Valid CargoEleicaoIdDTO id) {
        return this.cargoEleicaoMapper.toCargoEleicaoRetrievalDTO(this.cargoEleicaoService.findById(id));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<CargoEleicaoRetrievalDTO> findCargosEleicoes() {
        return this.cargoEleicaoService
            .findAll()
            .stream()
            .map(this.cargoEleicaoMapper::toCargoEleicaoRetrievalDTO)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/candidaturas")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<CandidaturaRetrievalDTO> findCandidaturas(@Valid CargoEleicaoIdDTO id) {
        return this.cargoEleicaoService
            .findCandidaturas(id)
            .stream()
            .map(this.candidaturaMapper::toCandidaturaRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/apuracoes-votos-cargos-boletim-urna")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<ApuracaoVotosCargoBoletimUrnaRetrievalDTO> findApuracoesVotosCargosBoletimUrna(@Valid CargoEleicaoIdDTO id) {
        return this.cargoEleicaoService
            .findApuracoesVotosCargosBoletimUrna(id)
            .stream()
            .map(this.apuracaoVotosCargoBoletimUrnaMapper::toApuracaoVotosCargoBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }

    @GetMapping(value = "/apuracoes-votos-partidos-boletim-urna")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<ApuracaoVotosPartidoBoletimUrnaRetrievalDTO> findApuracoesVotosPartidosBoletimUrna(@Valid CargoEleicaoIdDTO id) {
        return this.cargoEleicaoService
            .findApuracoesVotosPartidosBoletimUrna(id)
            .stream()
            .map(this.apuracaoVotosPartidoBoletimUrnaMapper::toApuracaoVotosPartidoBoletimUrnaRetrievalDTO)
            .collect(Collectors.toSet());
    }
}
