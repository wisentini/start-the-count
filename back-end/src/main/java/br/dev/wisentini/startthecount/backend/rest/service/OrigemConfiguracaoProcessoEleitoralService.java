package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.OrigemConfiguracaoProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.repository.OrigemConfiguracaoProcessoEleitoralRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrigemConfiguracaoProcessoEleitoralService {

    private final OrigemConfiguracaoProcessoEleitoralRepository origemConfiguracaoProcessoEleitoralRepository;

    private final ProcessoEleitoralService processoEleitoralService;

    public OrigemConfiguracaoProcessoEleitoral findById(int id) {
        return this.origemConfiguracaoProcessoEleitoralRepository
            .findById(id)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma origem de configuração de processo eleitoral com o ID %d.", id));
            });
    }

    public OrigemConfiguracaoProcessoEleitoral findByNomeAbreviado(String nomeAbreviado) {
        return this.origemConfiguracaoProcessoEleitoralRepository
            .findByNomeAbreviadoEqualsIgnoreCase(nomeAbreviado)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma origem de configuração de processo eleitoral com o nome abreviado \"%s\".", nomeAbreviado));
            });
    }

    public List<OrigemConfiguracaoProcessoEleitoral> findAll() {
        return this.origemConfiguracaoProcessoEleitoralRepository.findAll();
    }

    public OrigemConfiguracaoProcessoEleitoral getIfExistsOrElseSave(OrigemConfiguracaoProcessoEleitoral origemConfiguracaoProcessoEleitoral) {
        if (this.origemConfiguracaoProcessoEleitoralRepository.existsByNomeAbreviadoEqualsIgnoreCase(origemConfiguracaoProcessoEleitoral.getNomeAbreviado())) {
            return this.findByNomeAbreviado(origemConfiguracaoProcessoEleitoral.getNomeAbreviado());
        }

        return this.origemConfiguracaoProcessoEleitoralRepository.save(origemConfiguracaoProcessoEleitoral);
    }

    public void deleteByNomeAbreviado(String nomeAbreviado) {
        if (!this.origemConfiguracaoProcessoEleitoralRepository.existsByNomeAbreviadoEqualsIgnoreCase(nomeAbreviado)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma origem de configuração de processo eleitoral com o nome abreviado \"%s\".", nomeAbreviado));
        }

        this.processoEleitoralService.deleteByOrigemConfiguracao(nomeAbreviado);

        this.origemConfiguracaoProcessoEleitoralRepository.deleteByNomeAbreviadoEqualsIgnoreCase(nomeAbreviado);
    }
}
