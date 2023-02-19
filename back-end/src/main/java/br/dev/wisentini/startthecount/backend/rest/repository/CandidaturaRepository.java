package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Candidatura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Integer> {

    boolean existsByCandidatoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(Integer numeroTSECandidato, Integer codigoTSECargo, Integer codigoTSEEleicao);

    Optional<Candidatura> findByCandidatoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(Integer numeroTSECandidato, Integer codigoTSECargo, Integer codigoTSEEleicao);

    List<Candidatura> findByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao);

    List<Candidatura> findByPartidoNumeroTSE(Integer numeroTSEPartido);

    List<Candidatura> findByCandidatoCodigoTSEEqualsIgnoreCase(String codigoTSECandidato);

    @Modifying
    @Transactional
    void deleteByCandidatoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(Integer numeroTSECandidato, Integer codigoTSECargo, Integer codigoTSEEleicao);

    @Modifying
    @Transactional
    void deleteByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao);

    @Modifying
    @Transactional
    void deleteByPartidoNumeroTSE(Integer numeroTSEPartido);

    @Modifying
    @Transactional
    void deleteByCandidatoCodigoTSEEqualsIgnoreCase(String codigoTSECandidato);
}
