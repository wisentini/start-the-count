package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.UF;
import br.dev.wisentini.startthecount.backend.rest.repository.RegiaoRepository;
import br.dev.wisentini.startthecount.backend.rest.model.Regiao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegiaoService {

    private final RegiaoRepository regiaoRepository;

    private final UFService ufService;

    public Regiao findBySigla(String sigla) {
        return this.regiaoRepository
            .findBySiglaEqualsIgnoreCase(sigla)
            .orElseThrow(() -> {
              throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma região com a sigla \"%s\".", sigla));
            });
    }

    public List<Regiao> findAll() {
        return this.regiaoRepository.findAll();
    }

    public List<UF> findUFs(String sigla) {
        return this.ufService.findByRegiao(sigla);
    }
}
