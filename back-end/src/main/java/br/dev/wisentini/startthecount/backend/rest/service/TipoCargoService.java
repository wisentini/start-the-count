package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.repository.TipoCargoRepository;
import br.dev.wisentini.startthecount.backend.rest.model.TipoCargo;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCargoService {

    private final TipoCargoRepository tipoCargoRepository;

    private final CargoService cargoService;

    public TipoCargo findByCodigoTSE(int codigoTSE) {
        return this.tipoCargoRepository
            .findByCodigoTSE(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum tipo de cargo com o código do TSE %d.", codigoTSE));
            });
    }

    public List<TipoCargo> findAll() {
        return this.tipoCargoRepository.findAll();
    }

    public TipoCargo getIfExistsOrElseSave(TipoCargo tipoCargo) {
        if (this.tipoCargoRepository.existsByCodigoTSE(tipoCargo.getCodigoTSE())) {
            return this.findByCodigoTSE(tipoCargo.getCodigoTSE());
        }

        return this.tipoCargoRepository.save(tipoCargo);
    }

    public void deleteByCodigoTSE(int codigoTSE) {
        if (!this.tipoCargoRepository.existsByCodigoTSE(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum tipo de cargo com o código %d.", codigoTSE));
        }

        this.cargoService.deleteByTipo(codigoTSE);

        this.tipoCargoRepository.deleteByCodigoTSE(codigoTSE);
    }
}
