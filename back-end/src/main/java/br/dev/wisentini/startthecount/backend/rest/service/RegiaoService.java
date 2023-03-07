package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.UF;
import br.dev.wisentini.startthecount.backend.rest.repository.RegiaoRepository;
import br.dev.wisentini.startthecount.backend.rest.model.Regiao;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "regiao")
public class RegiaoService {

    private final RegiaoRepository regiaoRepository;

    @Cacheable(key = "#sigla")
    public Regiao findBySigla(String sigla) {
        return this.regiaoRepository
            .findBySiglaEqualsIgnoreCase(sigla)
            .orElseThrow(() -> {
              throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma região com a sigla \"%s\".", sigla));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<Regiao> findAll() {
        return this.regiaoRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%s', #root.methodName, #sigla)")
    public Set<UF> findUFs(String sigla) {
        return this.findBySigla(sigla).getUfs();
    }
}
