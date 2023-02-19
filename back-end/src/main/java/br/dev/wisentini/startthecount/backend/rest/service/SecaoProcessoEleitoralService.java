package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.LocalVotacaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoProcessoEleitoralIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoProcessoEleitoralMapper;
import br.dev.wisentini.startthecount.backend.rest.model.ProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.repository.SecaoProcessoEleitoralRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecaoProcessoEleitoralService {

    private final SecaoProcessoEleitoralRepository secaoProcessoEleitoralRepository;

    private final SecaoProcessoEleitoralMapper secaoProcessoEleitoralMapper;

    public SecaoProcessoEleitoral findById(SecaoProcessoEleitoralIdDTO id) {
        return this.secaoProcessoEleitoralRepository
            .findBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEProcessoEleitoral()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma instância de SecaoProcessoEleitoral identificada por %s.", id));
            });
    }

    public List<SecaoProcessoEleitoral> findAll() {
        return this.secaoProcessoEleitoralRepository.findAll();
    }

    public List<Secao> findSecoesByProcessoEleitoral(Integer codigoTSEProcessoEleitoral) {
        return this.secaoProcessoEleitoralRepository.findSecoesByProcessoEleitoral(codigoTSEProcessoEleitoral);
    }

    public List<ProcessoEleitoral> findProcessosEleitoraisBySecao(SecaoIdDTO id) {
        return this.secaoProcessoEleitoralRepository.findProcessosEleitoraisBySecao(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF()
        );
    }

    public SecaoProcessoEleitoral getIfExistsOrElseSave(SecaoProcessoEleitoral secaoProcessoEleitoral) {
        if (this.secaoProcessoEleitoralRepository.existsBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
            secaoProcessoEleitoral.getSecao().getNumeroTSE(),
            secaoProcessoEleitoral.getSecao().getZona().getNumeroTSE(),
            secaoProcessoEleitoral.getSecao().getZona().getUF().getSigla(),
            secaoProcessoEleitoral.getProcessoEleitoral().getCodigoTSE()
        )) {
            return this.findById(this.secaoProcessoEleitoralMapper.toSecaoProcessoEleitoralIdDTO(secaoProcessoEleitoral));
        }

        return this.secaoProcessoEleitoralRepository.save(secaoProcessoEleitoral);
    }

    public void deleteById(SecaoProcessoEleitoralIdDTO id) {
        id.validate();

        if (!this.secaoProcessoEleitoralRepository.existsBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEProcessoEleitoral()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma instância de SecaoProcessoEleitoral identificada por %s.", id));
        }

        this.secaoProcessoEleitoralRepository.deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEProcessoEleitoral()
        );
    }

    public void deleteBySecao(SecaoIdDTO secaoId) {
        secaoId.validate();

        this.secaoProcessoEleitoralRepository.deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(
            secaoId.getNumeroTSESecao(),
            secaoId.getNumeroTSEZona(),
            secaoId.getSiglaUF()
        );
    }

    public void deleteByProcessoEleitoral(int codigoTSEProcessoEleitoral) {
        this.secaoProcessoEleitoralRepository.deleteByProcessoEleitoralCodigoTSE(codigoTSEProcessoEleitoral);
    }

    public void deleteByLocalVotacao(LocalVotacaoIdDTO localVotacaoId) {
        localVotacaoId.validate();

        this.secaoProcessoEleitoralRepository.deleteByLocalVotacaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(
            localVotacaoId.getNumeroTSELocalVotacao(),
            localVotacaoId.getNumeroTSEZona(),
            localVotacaoId.getSiglaUF()
        );
    }
}
