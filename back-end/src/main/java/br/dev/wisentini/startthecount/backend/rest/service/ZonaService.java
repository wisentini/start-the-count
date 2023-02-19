package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.ZonaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.LocalVotacao;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;
import br.dev.wisentini.startthecount.backend.rest.repository.ZonaRepository;
import br.dev.wisentini.startthecount.backend.rest.dto.id.ZonaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Zona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZonaService {

    private final ZonaRepository zonaRepository;

    private final ZonaMapper zonaMapper;

    private final LocalVotacaoService localVotacaoService;

    private final SecaoService secaoService;

    @Autowired
    public ZonaService(ZonaRepository zonaRepository, ZonaMapper zonaMapper, LocalVotacaoService localVotacaoService, @Lazy SecaoService secaoService) {
        this.zonaRepository = zonaRepository;
        this.zonaMapper = zonaMapper;
        this.localVotacaoService = localVotacaoService;
        this.secaoService = secaoService;
    }

    public Zona findById(ZonaIdDTO id) {
        id.validate();

        return this.zonaRepository
            .findByNumeroTSEAndUfSiglaEqualsIgnoreCase(id.getNumeroTSEZona(), id.getSiglaUF())
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma zona identificada por %s.", id));
            });
    }

    public List<Zona> findAll() {
        return this.zonaRepository.findAll();
    }

    public List<Zona> findByUF(String siglaUF) {
        return this.zonaRepository.findByUfSiglaEqualsIgnoreCase(siglaUF);
    }

    public List<Secao> findSecoes(ZonaIdDTO id) {
        id.validate();

        return this.secaoService.findByZona(id);
    }

    public List<LocalVotacao> findLocaisVotacao(ZonaIdDTO id) {
        id.validate();

        return this.localVotacaoService.findByZona(id);
    }

    public Zona getIfExistsOrElseSave(Zona zona) {
        if (this.zonaRepository.existsByNumeroTSEAndUfSiglaEqualsIgnoreCase(zona.getNumeroTSE(), zona.getUF().getSigla())) {
            return this.findById(this.zonaMapper.toZonaIdDTO(zona));
        }

        return this.zonaRepository.save(zona);
    }

    public void deleteById(ZonaIdDTO id) {
        id.validate();

        if (!this.zonaRepository.existsByNumeroTSEAndUfSiglaEqualsIgnoreCase(id.getNumeroTSEZona(), id.getSiglaUF())) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma zona identificada por %s.", id));
        }

        this.secaoService.deleteByZona(id);
        this.localVotacaoService.deleteByZona(id);

        this.zonaRepository.deleteByNumeroTSEAndUfSiglaEqualsIgnoreCase(id.getNumeroTSEZona(), id.getSiglaUF());
    }
}
