package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.repository.ProcessoEleitoralRepository;
import br.dev.wisentini.startthecount.backend.rest.model.ProcessoEleitoral;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessoEleitoralService {

    private final ProcessoEleitoralRepository processoEleitoralRepository;

    private final PleitoService pleitoService;

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    public ProcessoEleitoral findByCodigoTSE(int codigoTSE) {
        return this.processoEleitoralRepository
            .findByCodigoTSE(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum processo eleitoral com o código do TSE %d.", codigoTSE));
            });
    }

    public List<ProcessoEleitoral> findAll() {
        return this.processoEleitoralRepository.findAll();
    }

    public ProcessoEleitoral getIfExistsOrElseSave(ProcessoEleitoral processoEleitoral) {
        if (this.processoEleitoralRepository.existsByCodigoTSE(processoEleitoral.getCodigoTSE())) {
            return this.findByCodigoTSE(processoEleitoral.getCodigoTSE());
        }

        return this.processoEleitoralRepository.save(processoEleitoral);
    }

    public void deleteByCodigoTSE(int codigoTSE) {
        if (!this.processoEleitoralRepository.existsByCodigoTSE(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum processo eleitoral com o código do TSE %d.", codigoTSE));
        }

        this.pleitoService.deleteByProcessoEleitoral(codigoTSE);
        this.secaoProcessoEleitoralService.deleteByProcessoEleitoral(codigoTSE);

        this.processoEleitoralRepository.deleteByCodigoTSE(codigoTSE);
    }

    public void deleteByOrigemConfiguracao(String nomeAbreviadoOrigemConfiguracao) {
        this.processoEleitoralRepository
            .findByOrigemConfiguracaoNomeAbreviadoEqualsIgnoreCase(nomeAbreviadoOrigemConfiguracao)
            .forEach(processoEleitoral -> {
                this.pleitoService.deleteByProcessoEleitoral(processoEleitoral.getCodigoTSE());
                this.secaoProcessoEleitoralService.deleteByProcessoEleitoral(processoEleitoral.getCodigoTSE());
            });

        this.processoEleitoralRepository.deleteByOrigemConfiguracaoNomeAbreviadoEqualsIgnoreCase(nomeAbreviadoOrigemConfiguracao);
    }
}
