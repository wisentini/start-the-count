package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;
import br.dev.wisentini.startthecount.backend.rest.repository.EleicaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EleicaoService {

    private final EleicaoRepository eleicaoRepository;

    private final CargoEleicaoService cargoEleicaoService;

    @Autowired
    public EleicaoService(EleicaoRepository eleicaoRepository, @Lazy CargoEleicaoService cargoEleicaoService) {
        this.eleicaoRepository = eleicaoRepository;
        this.cargoEleicaoService = cargoEleicaoService;
    }

    public Eleicao findByCodigoTSE(Integer codigoTSE) {
        return this.eleicaoRepository
            .findByCodigoTSE(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma eleição com o código %d.", codigoTSE));
            });
    }

    public List<Eleicao> findAll() {
        return this.eleicaoRepository.findAll();
    }

    public Eleicao getIfExistsOrElseSave(Eleicao eleicao) {
        if (this.eleicaoRepository.existsByCodigoTSE(eleicao.getCodigoTSE())) {
            return this.findByCodigoTSE(eleicao.getCodigoTSE());
        }

        return this.eleicaoRepository.save(eleicao);
    }

    public void deleteByCodigoTSE(Integer codigoTSE) {
        if (!this.eleicaoRepository.existsByCodigoTSE(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma eleição com o código %d.", codigoTSE));
        }

        this.cargoEleicaoService.deleteByEleicao(codigoTSE);

        this.eleicaoRepository.deleteByCodigoTSE(codigoTSE);
    }

    public void deleteByPleito(Integer codigoTSEPleito) {
        this.eleicaoRepository
            .findByPleitoCodigoTSE(codigoTSEPleito)
            .forEach(eleicao -> this.cargoEleicaoService.deleteByEleicao(eleicao.getCodigoTSE()));

        this.eleicaoRepository.deleteByPleitoCodigoTSE(codigoTSEPleito);
    }
}
