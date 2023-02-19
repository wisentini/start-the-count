package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.LocalVotacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LocalVotacaoRepository extends JpaRepository<LocalVotacao, Integer> {

    boolean existsByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSE, Integer numeroTSEZona, String siglaUF);

    Optional<LocalVotacao> findByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSE, Integer numeroTSEZona, String siglaUF);

    List<LocalVotacao> findByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSEZona, String siglaUF);

    List<LocalVotacao> findByMunicipioCodigoTSE(Integer codigoTSEMunicipio);

    @Modifying
    @Transactional
    void deleteByNumeroTSEAndZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSE, Integer numeroTSEZona, String siglaUF);

    @Modifying
    @Transactional
    void deleteByZonaNumeroTSEAndZonaUfSiglaEqualsIgnoreCase(Integer numeroTSEZona, String siglaUF);

    @Modifying
    @Transactional
    void deleteByMunicipioCodigoTSE(Integer codigoTSEMunicipio);
}
