package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.repository.CargoRepository;
import br.dev.wisentini.startthecount.backend.rest.model.Cargo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    private final CargoEleicaoService cargoEleicaoService;

    @Autowired
    public CargoService(CargoRepository cargoRepository, @Lazy CargoEleicaoService cargoEleicaoService) {
        this.cargoRepository = cargoRepository;
        this.cargoEleicaoService = cargoEleicaoService;
    }

    public Cargo findByCodigoTSE(int codigoTSE) {
        return this.cargoRepository
            .findByCodigoTSE(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum cargo com o código %d.", codigoTSE));
            });
    }

    public List<Cargo> findAll() {
        return this.cargoRepository.findAll();
    }

    public Cargo getIfExistsOrElseSave(Cargo cargo) {
        if (this.cargoRepository.existsByCodigoTSE(cargo.getCodigoTSE())) {
            return this.findByCodigoTSE(cargo.getCodigoTSE());
        }

        return this.cargoRepository.save(cargo);
    }

    public void deleteByCodigoTSE(int codigoTSE) {
        if (!this.cargoRepository.existsByCodigoTSE(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum cargo com o código %d.", codigoTSE));
        }

        this.cargoEleicaoService.deleteByCargo(codigoTSE);

        this.cargoRepository.deleteByCodigoTSE(codigoTSE);
    }

    public void deleteByTipo(int codigoTSETipoCargo) {
        this.cargoRepository
            .findByTipoCodigoTSE(codigoTSETipoCargo)
            .forEach(cargo -> this.cargoEleicaoService.deleteByCargo(cargo.getCodigoTSE()));

        this.cargoRepository.deleteByTipoCodigoTSE(codigoTSETipoCargo);
    }
}
