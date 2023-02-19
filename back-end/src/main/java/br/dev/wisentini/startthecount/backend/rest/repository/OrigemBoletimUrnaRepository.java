package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.OrigemBoletimUrna;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrigemBoletimUrnaRepository extends JpaRepository<OrigemBoletimUrna, Integer> {

    boolean existsByNomeAbreviadoEqualsIgnoreCase(String nomeAbreviado);

    Optional<OrigemBoletimUrna> findByNomeAbreviadoEqualsIgnoreCase(String nomeAbreviado);

    void deleteByNomeAbreviadoEqualsIgnoreCase(String nomeAbreviado);
}
