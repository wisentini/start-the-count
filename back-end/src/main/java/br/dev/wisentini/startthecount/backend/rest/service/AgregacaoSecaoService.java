package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.AgregacaoSecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.AgregacaoSecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.AgregacaoSecao;
import br.dev.wisentini.startthecount.backend.rest.repository.AgregacaoSecaoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgregacaoSecaoService {

    private final AgregacaoSecaoRepository agregacaoSecaoRepository;

    private final AgregacaoSecaoMapper agregacaoSecaoMapper;

    public AgregacaoSecao findById(AgregacaoSecaoIdDTO id) {
        return this.agregacaoSecaoRepository
            .findBySecaoPrincipalNumeroTSEAndSecaoPrincipalZonaNumeroTSEAndSecaoPrincipalZonaUfSiglaEqualsIgnoreCaseAndSecaoAgregadaNumeroTSEAndSecaoAgregadaZonaNumeroTSEAndSecaoAgregadaZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
                id.getNumeroTSESecaoPrincipal(),
                id.getNumeroTSEZonaSecaoPrincipal(),
                id.getSiglaUFSecaoPrincipal(),
                id.getNumeroTSESecaoAgregada(),
                id.getNumeroTSEZonaSecaoAgregada(),
                id.getSiglaUFSecaoAgregada(),
                id.getCodigoTSEProcessoEleitoral()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma agregação de seção identificada por %s.", id));
            });
    }

    public List<AgregacaoSecao> findAll() {
        return this.agregacaoSecaoRepository.findAll();
    }

    public AgregacaoSecao getIfExistsOrElseSave(AgregacaoSecao agregacaoSecao) {
        if (this.agregacaoSecaoRepository.existsBySecaoPrincipalNumeroTSEAndSecaoPrincipalZonaNumeroTSEAndSecaoPrincipalZonaUfSiglaEqualsIgnoreCaseAndSecaoAgregadaNumeroTSEAndSecaoAgregadaZonaNumeroTSEAndSecaoAgregadaZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
            agregacaoSecao.getSecaoPrincipal().getNumeroTSE(),
            agregacaoSecao.getSecaoPrincipal().getZona().getNumeroTSE(),
            agregacaoSecao.getSecaoPrincipal().getZona().getUF().getSigla(),
            agregacaoSecao.getSecaoAgregada().getNumeroTSE(),
            agregacaoSecao.getSecaoAgregada().getZona().getNumeroTSE(),
            agregacaoSecao.getSecaoAgregada().getZona().getUF().getSigla(),
            agregacaoSecao.getProcessoEleitoral().getCodigoTSE()
        )) {
            return this.findById(this.agregacaoSecaoMapper.toAgregacaoSecaoIdDTO(agregacaoSecao));
        }

        return this.agregacaoSecaoRepository.save(agregacaoSecao);
    }

    public void deleteById(AgregacaoSecaoIdDTO id) {
        id.validate();

        if (!this.agregacaoSecaoRepository.existsBySecaoPrincipalNumeroTSEAndSecaoPrincipalZonaNumeroTSEAndSecaoPrincipalZonaUfSiglaEqualsIgnoreCaseAndSecaoAgregadaNumeroTSEAndSecaoAgregadaZonaNumeroTSEAndSecaoAgregadaZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
            id.getNumeroTSESecaoPrincipal(),
            id.getNumeroTSEZonaSecaoPrincipal(),
            id.getSiglaUFSecaoPrincipal(),
            id.getNumeroTSESecaoAgregada(),
            id.getNumeroTSEZonaSecaoAgregada(),
            id.getSiglaUFSecaoAgregada(),
            id.getCodigoTSEProcessoEleitoral()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma agregação de seção identificada por %s.", id));
        }

        this.agregacaoSecaoRepository.deleteBySecaoPrincipalNumeroTSEAndSecaoPrincipalZonaNumeroTSEAndSecaoPrincipalZonaUfSiglaEqualsIgnoreCaseAndSecaoAgregadaNumeroTSEAndSecaoAgregadaZonaNumeroTSEAndSecaoAgregadaZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
            id.getNumeroTSESecaoPrincipal(),
            id.getNumeroTSEZonaSecaoPrincipal(),
            id.getSiglaUFSecaoPrincipal(),
            id.getNumeroTSESecaoAgregada(),
            id.getNumeroTSEZonaSecaoAgregada(),
            id.getSiglaUFSecaoAgregada(),
            id.getCodigoTSEProcessoEleitoral()
        );
    }

    public void deleteBySecaoPrincipal(SecaoIdDTO secaoPrincipalId) {
        secaoPrincipalId.validate();

        this.agregacaoSecaoRepository.deleteBySecaoPrincipalNumeroTSEAndSecaoPrincipalZonaNumeroTSEAndSecaoPrincipalZonaUfSiglaEqualsIgnoreCase(
            secaoPrincipalId.getNumeroTSESecao(),
            secaoPrincipalId.getNumeroTSEZona(),
            secaoPrincipalId.getSiglaUF()
        );
    }

    public void deleteBySecaoAgregada(SecaoIdDTO secaoAgregadaId) {
        secaoAgregadaId.validate();

        this.agregacaoSecaoRepository.deleteBySecaoAgregadaNumeroTSEAndSecaoAgregadaZonaNumeroTSEAndSecaoAgregadaZonaUfSiglaEqualsIgnoreCase(
            secaoAgregadaId.getNumeroTSESecao(),
            secaoAgregadaId.getNumeroTSEZona(),
            secaoAgregadaId.getSiglaUF()
        );
    }

    public void deleteByProcessoEleitoral(int codigoTSEProcessoEleitoral) {
        this.agregacaoSecaoRepository.deleteByProcessoEleitoralCodigoTSE(codigoTSEProcessoEleitoral);
    }
}
