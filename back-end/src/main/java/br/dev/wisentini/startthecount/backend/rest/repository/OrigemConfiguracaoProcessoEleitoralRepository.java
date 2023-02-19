package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.OrigemConfiguracaoProcessoEleitoral;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrigemConfiguracaoProcessoEleitoralRepository extends JpaRepository<OrigemConfiguracaoProcessoEleitoral, Integer> {

    boolean existsByNomeAbreviadoEqualsIgnoreCase(String nomeAbreviado);

    Optional<OrigemConfiguracaoProcessoEleitoral> findByNomeAbreviadoEqualsIgnoreCase(String nomeAbreviado);

    void deleteByNomeAbreviadoEqualsIgnoreCase(String nomeAbreviado);
}
