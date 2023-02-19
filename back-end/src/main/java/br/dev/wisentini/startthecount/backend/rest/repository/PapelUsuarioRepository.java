package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Papel;
import br.dev.wisentini.startthecount.backend.rest.model.PapelUsuario;
import br.dev.wisentini.startthecount.backend.rest.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PapelUsuarioRepository extends JpaRepository<PapelUsuario, Integer> {

    boolean existsByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(String username, String nomePapel);

    Optional<PapelUsuario> findByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(String username, String nomePapel);

    @Query(value = "SELECT pu.papel FROM PapelUsuario AS pu WHERE pu.usuario.username ILIKE :username")
    List<Papel> findPapeisByUsuario(String username);

    @Query(value = "SELECT pu.usuario FROM PapelUsuario AS pu WHERE pu.papel.nome ILIKE :nomePapel")
    List<Usuario> findUsuariosByPapel(String nomePapel);

    void deleteByUsuarioUsernameEqualsIgnoreCaseAndPapelNomeEqualsIgnoreCase(String username, String nomePapel);
}
