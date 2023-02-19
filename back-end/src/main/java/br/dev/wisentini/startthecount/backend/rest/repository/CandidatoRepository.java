package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Candidato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CandidatoRepository extends JpaRepository<Candidato, Integer> {

    Optional<Candidato> findByCodigoTSEEqualsIgnoreCase(String codigoTSE);

    boolean existsByCodigoTSEEqualsIgnoreCase(String codigoTSE);

    @Modifying
    @Transactional
    void deleteByCodigoTSEEqualsIgnoreCase(String codigoTSE);
}
