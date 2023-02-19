package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Pleito;
import br.dev.wisentini.startthecount.backend.rest.repository.PleitoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PleitoService {

    private final PleitoRepository pleitoRepository;

    private final EleicaoService eleicaoService;

    private final SecaoPleitoService secaoPleitoService;

    public Pleito findByCodigoTSE(int codigoTSE) {
        return this.pleitoRepository
            .findByCodigoTSE(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum pleito com o código %d.", codigoTSE));
            });
    }

    public List<Pleito> findAll() {
        return this.pleitoRepository.findAll();
    }

    public Pleito getIfExistsOrElseSave(Pleito pleito) {
        if (this.pleitoRepository.existsByCodigoTSE(pleito.getCodigoTSE())) {
            return this.findByCodigoTSE(pleito.getCodigoTSE());
        }

        return this.pleitoRepository.save(pleito);
    }

    public void deleteByCodigoTSE(int codigoTSE) {
        if (!this.pleitoRepository.existsByCodigoTSE(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum pleito com o código %d.", codigoTSE));
        }

        this.eleicaoService.deleteByPleito(codigoTSE);
        this.secaoPleitoService.deleteByPleito(codigoTSE);

        this.pleitoRepository.deleteByCodigoTSE(codigoTSE);
    }

    public void deleteByProcessoEleitoral(int codigoTSEProcessoEleitoral) {
        this.pleitoRepository.findByProcessoEleitoralCodigoTSE(codigoTSEProcessoEleitoral)
            .forEach(pleito -> {
                this.eleicaoService.deleteByPleito(pleito.getCodigoTSE());
                this.secaoPleitoService.deleteByPleito(pleito.getCodigoTSE());
            })
        ;

        this.pleitoRepository.deleteByProcessoEleitoralCodigoTSE(codigoTSEProcessoEleitoral);
    }
}
