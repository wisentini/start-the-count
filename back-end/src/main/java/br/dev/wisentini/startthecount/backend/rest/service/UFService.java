package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Municipio;
import br.dev.wisentini.startthecount.backend.rest.model.UF;
import br.dev.wisentini.startthecount.backend.rest.model.Zona;
import br.dev.wisentini.startthecount.backend.rest.repository.UFRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UFService {

    private final UFRepository ufRepository;

    private final MunicipioService municipioService;

    private final ZonaService zonaService;

    public UF findBySigla(String sigla) {
        return this.ufRepository
            .findBySiglaEqualsIgnoreCase(sigla)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma UF com a sigla \"%s\".", sigla));
            });
    }

    public List<UF> findAll() {
        return this.ufRepository.findAll();
    }

    public List<UF> findByRegiao(String siglaRegiao) {
        return this.ufRepository.findByRegiaoSiglaEqualsIgnoreCase(siglaRegiao);
    }

    public List<Municipio> findMunicipios(String sigla) {
        return this.municipioService.findByUF(sigla);
    }

    public List<Zona> findZonas(String sigla) {
        return this.zonaService.findByUF(sigla);
    }
}
