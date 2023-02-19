package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Secao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SecaoRepository extends JpaRepository<Secao, Integer> {

    boolean existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSE, Integer numeroTSEZona, String siglaUF);

    Optional<Secao> findByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSE, Integer numeroTSEZona, String siglaUF);

    List<Secao> findByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSEZona, String siglaUF);

    @Modifying
    @Transactional
    void deleteByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSE, Integer numeroTSEZona, String siglaUF);

    @Modifying
    @Transactional
    void deleteByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSEZona, String siglaUF);
}
