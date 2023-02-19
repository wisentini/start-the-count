package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoMapper;
import br.dev.wisentini.startthecount.backend.rest.repository.SecaoRepository;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.ZonaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecaoService {

    private final SecaoRepository secaoRepository;

    private final SecaoMapper secaoMapper;

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    private final SecaoPleitoService secaoPleitoService;

    public Secao findById(SecaoIdDTO id) {
        return this.secaoRepository
            .findByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSESecao(), id.getNumeroTSEZona(), id.getSiglaUF())
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma seção identificada por %s.", id));
            });
    }

    public List<Secao> findAll() {
        return this.secaoRepository.findAll();
    }

    public List<Secao> findByZona(ZonaIdDTO id) {
        id.validate();

        return this.secaoRepository.findByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            id.getNumeroTSEZona(),
            id.getSiglaUF()
        );
    }

    public Secao getIfExistsOrElseSave(Secao secao) {
        if (this.secaoRepository.existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            secao.getNumeroTSE(),
            secao.getZona().getNumeroTSE(),
            secao.getZona().getUF().getSigla()
        )) {
            return this.findById(this.secaoMapper.toSecaoIdDTO(secao));
        }

        return this.secaoRepository.save(secao);
    }

    public void deleteById(SecaoIdDTO id) {
        id.validate();

        if (!this.secaoRepository.existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(id.getNumeroTSESecao(), id.getNumeroTSEZona(), id.getSiglaUF())) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma seção identificada por %s.", id));
        }

        this.secaoProcessoEleitoralService.deleteBySecao(id);
        this.secaoPleitoService.deleteBySecao(id);

        this.secaoRepository.deleteByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF()
        );
    }

    public void deleteByZona(ZonaIdDTO zonaId) {
        zonaId.validate();

        this.secaoRepository
            .findByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(zonaId.getNumeroTSEZona(), zonaId.getSiglaUF())
            .forEach(secao -> {
                SecaoIdDTO id = this.secaoMapper.toSecaoIdDTO(secao);

                this.secaoProcessoEleitoralService.deleteBySecao(id);
                this.secaoPleitoService.deleteBySecao(id);
            });

        this.secaoRepository.deleteByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(
            zonaId.getNumeroTSEZona(),
            zonaId.getSiglaUF()
        );
    }
}
