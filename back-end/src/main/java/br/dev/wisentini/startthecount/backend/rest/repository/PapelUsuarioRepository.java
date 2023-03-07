package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.PapelUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PapelUsuarioRepository extends JpaRepository<PapelUsuario, Integer> {

    boolean existsByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(String username, String nomePapel);

    Optional<PapelUsuario> findByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(String username, String nomePapel);

    @Modifying
    @Transactional
    void deleteByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(String username, String nomePapel);

    @Modifying
    @Transactional
    void deleteByUsuarioUsernameEqualsIgnoreCase(String username);
}
