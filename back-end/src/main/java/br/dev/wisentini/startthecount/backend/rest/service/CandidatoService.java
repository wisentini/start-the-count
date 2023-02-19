package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Candidato;
import br.dev.wisentini.startthecount.backend.rest.repository.CandidatoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;

    private final CandidaturaService candidaturaService;

    @Autowired
    public CandidatoService(CandidatoRepository candidatoRepository, @Lazy CandidaturaService candidaturaService) {
        this.candidatoRepository = candidatoRepository;
        this.candidaturaService = candidaturaService;
    }

    public Candidato findByCodigoTSE(String codigoTSE) {
        return this.candidatoRepository
            .findByCodigoTSEEqualsIgnoreCase(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum candidato com o código %s.", codigoTSE));
            });
    }

    public List<Candidato> findAll() {
        return this.candidatoRepository.findAll();
    }

    public Candidato getIfExistsOrElseSave(Candidato candidato) {
        if (this.candidatoRepository.existsByCodigoTSEEqualsIgnoreCase(candidato.getCodigoTSE())) {
            return this.findByCodigoTSE(candidato.getCodigoTSE());
        }

        return this.candidatoRepository.save(candidato);
    }

    public void deleteByCodigoTSE(String codigoTSE) {
        if (!this.candidatoRepository.existsByCodigoTSEEqualsIgnoreCase(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum candidato com o código %s.", codigoTSE));
        }

        this.candidaturaService.deleteByCandidato(codigoTSE);

        this.candidatoRepository.deleteByCodigoTSEEqualsIgnoreCase(codigoTSE);
    }
}
