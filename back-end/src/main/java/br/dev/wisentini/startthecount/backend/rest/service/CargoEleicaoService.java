package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.CargoEleicaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.CargoEleicaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.*;
import br.dev.wisentini.startthecount.backend.rest.repository.CargoEleicaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "cargo-eleicao")
public class CargoEleicaoService {

    private final CargoEleicaoRepository cargoEleicaoRepository;

    private final CargoEleicaoMapper cargoEleicaoMapper;

    private final CandidaturaService candidaturaService;

    private final ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService;

    private final ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService;

    private final CachingService cachingService;

    @Autowired
    public CargoEleicaoService(
        CargoEleicaoRepository cargoEleicaoRepository,
        CargoEleicaoMapper cargoEleicaoMapper,
        @Lazy CandidaturaService candidaturaService,
        ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService,
        ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService,
        CachingService cachingService
    ) {
        this.cargoEleicaoRepository = cargoEleicaoRepository;
        this.cargoEleicaoMapper = cargoEleicaoMapper;
        this.candidaturaService = candidaturaService;
        this.apuracaoVotosCargoBoletimUrnaService = apuracaoVotosCargoBoletimUrnaService;
        this.apuracaoVotosPartidoBoletimUrnaService = apuracaoVotosPartidoBoletimUrnaService;
        this.cachingService = cachingService;
    }

    @Cacheable(key = "T(java.lang.String).format('%d:%d', #id.codigoTSECargo, #id.codigoTSEEleicao)")
    public CargoEleicao findById(CargoEleicaoIdDTO id) {
        return this.cargoEleicaoRepository
            .findByCargoCodigoTSEAndEleicaoCodigoTSE(id.getCodigoTSECargo(), id.getCodigoTSEEleicao())
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação entre cargo e eleição identificada por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<CargoEleicao> findAll() {
        return this.cargoEleicaoRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d', #root.methodName, #id.codigoTSECargo, #id.codigoTSEEleicao)")
    public Set<Candidatura> findCandidaturas(CargoEleicaoIdDTO id) {
        return this.findById(id).getCandidaturas();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d', #root.methodName, #id.codigoTSECargo, #id.codigoTSEEleicao)")
    public Set<ApuracaoVotosCargoBoletimUrna> findApuracoesVotosCargosBoletimUrna(CargoEleicaoIdDTO id) {
        return this.findById(id).getApuracoesVotosCargosBoletimUrna();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d', #root.methodName, #id.codigoTSECargo, #id.codigoTSEEleicao)")
    public Set<ApuracaoVotosPartidoBoletimUrna> findApuracoesVotosPartidosBoletimUrna(CargoEleicaoIdDTO id) {
        return this.findById(id).getApuracoesVotosPartidosBoletimUrna();
    }

    public CargoEleicao getIfExistsOrElseSave(CargoEleicao cargoEleicao) {
        if (this.cargoEleicaoRepository.existsByCargoCodigoTSEAndEleicaoCodigoTSE(
            cargoEleicao.getCargo().getCodigoTSE(),
            cargoEleicao.getEleicao().getCodigoTSE()
        )) {
            return this.findById(this.cargoEleicaoMapper.toCargoEleicaoIdDTO(cargoEleicao));
        }

        this.cachingService.evictAllCaches();

        return this.cargoEleicaoRepository.save(cargoEleicao);
    }

    public void deleteById(CargoEleicaoIdDTO id) {
        id.validate();

        if (!this.cargoEleicaoRepository.existsByCargoCodigoTSEAndEleicaoCodigoTSE(id.getCodigoTSECargo(), id.getCodigoTSEEleicao())) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação entre cargo e eleição identificada por %s.", id));
        }

        this.candidaturaService.deleteByCargoEleicao(id);
        this.apuracaoVotosCargoBoletimUrnaService.deleteByCargoEleicao(id);
        this.apuracaoVotosPartidoBoletimUrnaService.deleteByCargoEleicao(id);

        this.cargoEleicaoRepository.deleteByCargoCodigoTSEAndEleicaoCodigoTSE(id.getCodigoTSECargo(), id.getCodigoTSEEleicao());

        this.cachingService.evictAllCaches();
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

        this.cachingService.evictAllCaches();
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

        this.cachingService.evictAllCaches();
    }
}
