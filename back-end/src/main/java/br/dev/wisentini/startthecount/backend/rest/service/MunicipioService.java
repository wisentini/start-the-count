package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.LocalVotacao;
import br.dev.wisentini.startthecount.backend.rest.repository.MunicipioRepository;
import br.dev.wisentini.startthecount.backend.rest.model.Municipio;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "municipio")
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    private final LocalVotacaoService localVotacaoService;

    @Cacheable(key = "#codigoTSE")
    public Municipio findByCodigoTSE(int codigoTSE) {
        return this.municipioRepository
            .findByCodigoTSE(codigoTSE)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum município com o código %d.", codigoTSE));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<Municipio> findAll() {
        return this.municipioRepository.findAll();
    }

    public List<Municipio> findByUF(String siglaUF) {
        return this.municipioRepository.findByUfSiglaEqualsIgnoreCase(siglaUF);
    }

    public List<LocalVotacao> findLocaisVotacao(Integer codigoTSE) {
        return this.localVotacaoService.findByMunicipio(codigoTSE);
    }

    @CacheEvict(allEntries = true)
    public Municipio getIfExistsOrElseSave(Municipio municipio) {
        if (this.municipioRepository.existsByCodigoTSE(municipio.getCodigoTSE())) {
            return this.findByCodigoTSE(municipio.getCodigoTSE());
        }

        return this.municipioRepository.save(municipio);
    }

    @CacheEvict(allEntries = true)
    public void deleteByCodigoTSE(Integer codigoTSE) {
        if (!this.municipioRepository.existsByCodigoTSE(codigoTSE)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum município com o código %d.", codigoTSE));
        }

        this.localVotacaoService.deleteByMunicipio(codigoTSE);

        this.municipioRepository.deleteByCodigoTSE(codigoTSE);
    }
}
