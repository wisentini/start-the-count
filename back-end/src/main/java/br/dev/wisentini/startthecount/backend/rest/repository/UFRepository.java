package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.UF;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UFRepository extends JpaRepository<UF, Integer> {

    Optional<UF> findBySiglaEqualsIgnoreCase(String sigla);
}
