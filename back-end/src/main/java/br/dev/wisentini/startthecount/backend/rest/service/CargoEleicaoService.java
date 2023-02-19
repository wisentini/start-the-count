package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.CargoEleicaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.CargoEleicaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Cargo;
import br.dev.wisentini.startthecount.backend.rest.model.CargoEleicao;
import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;
import br.dev.wisentini.startthecount.backend.rest.repository.CargoEleicaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoEleicaoService {

    private final CargoEleicaoRepository cargoEleicaoRepository;

    private final CargoEleicaoMapper cargoEleicaoMapper;

    private final CandidaturaService candidaturaService;

    private final ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService;

    private final ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService;

    @Autowired
    public CargoEleicaoService(
        CargoEleicaoRepository cargoEleicaoRepository,
        CargoEleicaoMapper cargoEleicaoMapper,
        @Lazy CandidaturaService candidaturaService,
        ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService,
        ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService
    ) {
        this.cargoEleicaoRepository = cargoEleicaoRepository;
        this.cargoEleicaoMapper = cargoEleicaoMapper;
        this.candidaturaService = candidaturaService;
        this.apuracaoVotosCargoBoletimUrnaService = apuracaoVotosCargoBoletimUrnaService;
        this.apuracaoVotosPartidoBoletimUrnaService = apuracaoVotosPartidoBoletimUrnaService;
    }

    public CargoEleicao findById(CargoEleicaoIdDTO id) {
        return this.cargoEleicaoRepository
            .findByCargoCodigoTSEAndEleicaoCodigoTSE(id.getCodigoTSECargo(), id.getCodigoTSEEleicao())
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma instância de CargoEleicao identificada por %s.", id));
            });
    }

    public List<CargoEleicao> findAll() {
        return this.cargoEleicaoRepository.findAll();
    }

    public List<Cargo> findCargosByEleicao(Integer codigoTSEEleicao) {
        return this.cargoEleicaoRepository.findCargosByEleicao(codigoTSEEleicao);
    }

    public List<Eleicao> findEleicoesByCargo(Integer codigoTSECargo) {
        return this.cargoEleicaoRepository.findEleicoesByCargo(codigoTSECargo);
    }

    public CargoEleicao getIfExistsOrElseSave(CargoEleicao cargoEleicao) {
        if (this.cargoEleicaoRepository.existsByCargoCodigoTSEAndEleicaoCodigoTSE(
            cargoEleicao.getCargo().getCodigoTSE(),
            cargoEleicao.getEleicao().getCodigoTSE()
        )) {
            return this.findById(this.cargoEleicaoMapper.toCargoEleicaoIdDTO(cargoEleicao));
        }

        return this.cargoEleicaoRepository.save(cargoEleicao);
    }

    public void deleteById(CargoEleicaoIdDTO id) {
        id.validate();

        if (!this.cargoEleicaoRepository.existsByCargoCodigoTSEAndEleicaoCodigoTSE(id.getCodigoTSECargo(), id.getCodigoTSEEleicao())) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma instância de CargoEleicao identificada por %s.", id));
        }

        this.candidaturaService.deleteByCargoEleicao(id);
        this.apuracaoVotosCargoBoletimUrnaService.deleteByCargoEleicao(id);
        this.apuracaoVotosPartidoBoletimUrnaService.deleteByCargoEleicao(id);

        this.cargoEleicaoRepository.deleteByCargoCodigoTSEAndEleicaoCodigoTSE(id.getCodigoTSECargo(), id.getCodigoTSEEleicao());
    }

    public void deleteByCargo(Integer codigoTSECargo) {
        this.cargoEleicaoRepository
            .findByCargoCodigoTSE(codigoTSECargo)
            .forEach(cargoEleicao -> {
                this.candidaturaService.deleteByCargoEleicao(
                    this.cargoEleicaoMapper.toCargoEleicaoIdDTO(cargoEleicao)
                );

                this.apuracaoVotosCargoBoletimUrnaService.deleteByCargoEleicao(
                    this.cargoEleicaoMapper.toCargoEleicaoIdDTO(cargoEleicao)
                );

                this.apuracaoVotosPartidoBoletimUrnaService.deleteByCargoEleicao(
                    this.cargoEleicaoMapper.toCargoEleicaoIdDTO(cargoEleicao)
                );
            });

        this.cargoEleicaoRepository.deleteByCargoCodigoTSE(codigoTSECargo);
    }

    public void deleteByEleicao(Integer codigoTSEEleicao) {
        this.cargoEleicaoRepository
            .findByEleicaoCodigoTSE(codigoTSEEleicao)
            .forEach(cargoEleicao -> {
                this.candidaturaService.deleteByCargoEleicao(
                    this.cargoEleicaoMapper.toCargoEleicaoIdDTO(cargoEleicao)
                );

                this.apuracaoVotosCargoBoletimUrnaService.deleteByCargoEleicao(
                    this.cargoEleicaoMapper.toCargoEleicaoIdDTO(cargoEleicao)
                );

                this.apuracaoVotosPartidoBoletimUrnaService.deleteByCargoEleicao(
                    this.cargoEleicaoMapper.toCargoEleicaoIdDTO(cargoEleicao)
                );
            });

        this.cargoEleicaoRepository.deleteByEleicaoCodigoTSE(codigoTSEEleicao);
    }
}
