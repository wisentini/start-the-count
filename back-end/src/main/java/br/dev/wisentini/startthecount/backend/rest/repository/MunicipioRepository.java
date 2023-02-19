package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Municipio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

    boolean existsByCodigoTSE(Integer codigoTSE);

    Optional<Municipio> findByCodigoTSE(Integer codigoTSE);

    List<Municipio> findByUfSiglaEqualsIgnoreCase(String siglaUF);

    @Modifying
    @Transactional
    void deleteByCodigoTSE(Integer codigoTSE);
}
