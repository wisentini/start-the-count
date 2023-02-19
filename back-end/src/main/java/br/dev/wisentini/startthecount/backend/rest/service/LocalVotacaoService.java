package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.repository.LocalVotacaoRepository;
import br.dev.wisentini.startthecount.backend.rest.dto.id.LocalVotacaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.ZonaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.LocalVotacaoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.LocalVotacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalVotacaoService {

    private final LocalVotacaoRepository localVotacaoRepository;

    private final LocalVotacaoMapper localVotacaoMapper;

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    @Autowired
    public LocalVotacaoService(LocalVotacaoRepository localVotacaoRepository, @Lazy LocalVotacaoMapper localVotacaoMapper, SecaoProcessoEleitoralService secaoProcessoEleitoralService) {
        this.localVotacaoRepository = localVotacaoRepository;
        this.localVotacaoMapper = localVotacaoMapper;
        this.secaoProcessoEleitoralService = secaoProcessoEleitoralService;
    }

    public LocalVotacao findById(LocalVotacaoIdDTO id) {
        id.validate();

        return this.localVotacaoRepository
            .findByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSELocalVotacao(), id.getNumeroTSEZona(), id.getSiglaUF())
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum local de votação identificado por %s.", id));
            });
    }

    public List<LocalVotacao> findAll() {
        return this.localVotacaoRepository.findAll();
    }

    public List<LocalVotacao> findByMunicipio(Integer codigoTSEMunicipio) {
        return this.localVotacaoRepository.findByMunicipioCodigoTSE(codigoTSEMunicipio);
    }

    public List<LocalVotacao> findByZona(ZonaIdDTO id) {
        id.validate();

        return this.localVotacaoRepository.findByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            id.getNumeroTSEZona(),
            id.getSiglaUF()
        );
    }

    public LocalVotacao getIfExistsOrElseSave(LocalVotacao localVotacao) {
        if (this.localVotacaoRepository.existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            localVotacao.getNumeroTSE(),
            localVotacao.getZona().getNumeroTSE(),
            localVotacao.getZona().getUF().getSigla()
        )) {
            return this.findById(this.localVotacaoMapper.toLocalVotacaoIdDTO(localVotacao));
        }

        return this.localVotacaoRepository.save(localVotacao);
    }

    public void deleteById(LocalVotacaoIdDTO id) {
        id.validate();

        if (!this.localVotacaoRepository.existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSELocalVotacao(), id.getNumeroTSEZona(), id.getSiglaUF())) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum local de votação identificado por %s.", id));
        }

        this.secaoProcessoEleitoralService.deleteByLocalVotacao(id);

        this.localVotacaoRepository.deleteByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSELocalVotacao(), id.getNumeroTSEZona(), id.getSiglaUF());
    }

    public void deleteByZona(ZonaIdDTO zonaId) {
        zonaId.validate();

        this.localVotacaoRepository
            .findByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(zonaId.getNumeroTSEZona(), zonaId.getSiglaUF())
            .forEach(localVotacao -> this.secaoProcessoEleitoralService.deleteByLocalVotacao(
                this.localVotacaoMapper.toLocalVotacaoIdDTO(localVotacao)
            ));

        this.localVotacaoRepository.deleteByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(zonaId.getNumeroTSEZona(), zonaId.getSiglaUF());
    }

    public void deleteByMunicipio(int codigoTSEMunicipio) {
        this.localVotacaoRepository
            .findByMunicipioCodigoTSE(codigoTSEMunicipio)
            .forEach(localVotacao -> this.secaoProcessoEleitoralService.deleteByLocalVotacao(
                this.localVotacaoMapper.toLocalVotacaoIdDTO(localVotacao)
            ));

        this.localVotacaoRepository.deleteByMunicipioCodigoTSE(codigoTSEMunicipio);
    }
}
