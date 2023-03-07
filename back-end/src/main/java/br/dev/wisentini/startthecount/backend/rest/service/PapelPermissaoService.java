package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.PapelPermissaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.PapelPermissao;
import br.dev.wisentini.startthecount.backend.rest.repository.PapelPermissaoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "papel-permissao")
public class PapelPermissaoService {

    private final PapelPermissaoRepository papelPermissaoRepository;

    @Cacheable(key = "T(java.lang.String).format('%s:%s', #id.nomePapel, #id.nomePermissao)")
    public PapelPermissao findById(PapelPermissaoIdDTO id) {
        return this.papelPermissaoRepository
            .findByPapelNomeEqualsIgnoreCaseAndPermissaoNomeEqualsIgnoreCase(
                id.getNomePapel(),
                id.getNomePermissao()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação entre papel e permissão identificada por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<PapelPermissao> findAll() {
        return this.papelPermissaoRepository.findAll();
    }
}
