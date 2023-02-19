package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Regiao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegiaoRepository extends JpaRepository<Regiao, Integer> {

    Optional<Regiao> findBySiglaEqualsIgnoreCase(String sigla);
}
