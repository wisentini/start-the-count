package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Papel;
import br.dev.wisentini.startthecount.backend.rest.model.PapelPermissao;
import br.dev.wisentini.startthecount.backend.rest.model.Permissao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PapelPermissaoRepository extends JpaRepository<PapelPermissao, Integer> {

    Optional<PapelPermissao> findByPapelNomeEqualsIgnoreCaseAndPermissaoNomeEqualsIgnoreCase(String nomePapel, String nomePermissao);

    @Query(value = "SELECT pp.papel FROM PapelPermissao AS pp WHERE pp.permissao.nome ILIKE :nomePermissao")
    List<Papel> findPapeisByPermissao(String nomePermissao);

    @Query(value = "SELECT pp.permissao FROM PapelPermissao AS pp WHERE pp.papel.nome ILIKE :nomePapel")
    List<Permissao> findPermissoesByPapel(String nomePapel);
}
