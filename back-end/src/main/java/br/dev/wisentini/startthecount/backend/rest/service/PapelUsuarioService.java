package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.PapelUsuarioIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeJaExisteException;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.PapelUsuario;
import br.dev.wisentini.startthecount.backend.rest.repository.PapelUsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "papel-usuario")
public class PapelUsuarioService {

    private final PapelUsuarioRepository papelUsuarioRepository;

    private final CachingService cachingService;

    @Cacheable(key = "T(java.lang.String).format('%s:%s', #id.username, #id.nomePapel)")
    public PapelUsuario findById(PapelUsuarioIdDTO id) {
        return this.papelUsuarioRepository
            .findByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(
                id.getUsername(),
                id.getNomePapel()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação do usuário \"%s\" com o papel \"%s\".", id.getUsername(), id.getNomePapel()));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<PapelUsuario> findAll() {
        return this.papelUsuarioRepository.findAll();
    }

    public void save(PapelUsuario papelUsuario) {
        if (this.papelUsuarioRepository.existsByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(
            papelUsuario.getUsuario().getUsername(),
            papelUsuario.getPapel().getNome()
        )) {
            throw new EntidadeJaExisteException(String.format("Já existe uma relação do usuário \"%s\" com o papel \"%s\".", papelUsuario.getUsuario().getUsername(), papelUsuario.getPapel().getNome()));
        }

        this.papelUsuarioRepository.save(papelUsuario);

        this.cachingService.evictAllCaches();
    }

    public void deleteById(PapelUsuarioIdDTO id) {
        if (!this.papelUsuarioRepository.existsByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(
            id.getUsername(),
            id.getNomePapel()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação do usuário \"%s\" com o papel \"%s\".", id.getUsername(), id.getNomePapel()));
        }

        this.papelUsuarioRepository.deleteByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(
            id.getUsername(),
            id.getNomePapel()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteByUsuario(String username) {
        this.papelUsuarioRepository.deleteByUsuarioUsernameEqualsIgnoreCase(username);

        this.cachingService.evictAllCaches();
    }
}
