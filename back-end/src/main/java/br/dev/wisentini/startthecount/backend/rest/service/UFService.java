package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Municipio;
import br.dev.wisentini.startthecount.backend.rest.model.UF;
import br.dev.wisentini.startthecount.backend.rest.model.Zona;
import br.dev.wisentini.startthecount.backend.rest.repository.UFRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "uf")
public class UFService {

    private final UFRepository ufRepository;

    @Cacheable(key = "#sigla")
    public UF findBySigla(String sigla) {
        return this.ufRepository
            .findBySiglaEqualsIgnoreCase(sigla)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma UF com a sigla \"%s\".", sigla));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<UF> findAll() {
        return this.ufRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%s', #root.methodName, #sigla)")
    public Set<Municipio> findMunicipios(String sigla) {
        return this.findBySigla(sigla).getMunicipios();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%s', #root.methodName, #sigla)")
    public Set<Zona> findZonas(String sigla) {
        return this.findBySigla(sigla).getZonas();
    }
}
