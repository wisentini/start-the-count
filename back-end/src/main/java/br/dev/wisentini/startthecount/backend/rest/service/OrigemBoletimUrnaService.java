package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.repository.OrigemBoletimUrnaRepository;
import br.dev.wisentini.startthecount.backend.rest.model.OrigemBoletimUrna;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrigemBoletimUrnaService {

    private final OrigemBoletimUrnaRepository origemBoletimUrnaRepository;

    private final BoletimUrnaService boletimUrnaService;

    public OrigemBoletimUrna findByNomeAbreviado(String nomeAbreviado) {
        return this.origemBoletimUrnaRepository
            .findByNomeAbreviadoEqualsIgnoreCase(nomeAbreviado)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma origem de boletim de urna com o nome abreviado \"%s\".", nomeAbreviado));
            });
    }

    public List<OrigemBoletimUrna> findAll() {
        return this.origemBoletimUrnaRepository.findAll();
    }

    public OrigemBoletimUrna getIfExistsOrElseSave(OrigemBoletimUrna origemBoletimUrna) {
        if (this.origemBoletimUrnaRepository.existsByNomeAbreviadoEqualsIgnoreCase(origemBoletimUrna.getNomeAbreviado())) {
            return this.findByNomeAbreviado(origemBoletimUrna.getNomeAbreviado());
        }

        return this.origemBoletimUrnaRepository.save(origemBoletimUrna);
    }

    public void deleteByNomeAbreviado(String nomeAbreviado) {
        if (!this.origemBoletimUrnaRepository.existsByNomeAbreviadoEqualsIgnoreCase(nomeAbreviado)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma origem de boletim de urna com o nome abreviado \"%s\".", nomeAbreviado));
        }

        this.boletimUrnaService.deleteByOrigemBoletimUrna(nomeAbreviado);

        this.origemBoletimUrnaRepository.deleteByNomeAbreviadoEqualsIgnoreCase(nomeAbreviado);
    }
}
