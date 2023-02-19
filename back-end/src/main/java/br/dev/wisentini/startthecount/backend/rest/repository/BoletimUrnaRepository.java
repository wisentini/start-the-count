package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.BoletimUrna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BoletimUrnaRepository extends JpaRepository<BoletimUrna, Integer> {

    boolean existsBySecaoPleitoSecaoNumeroTSEAndSecaoPleitoSecaoZonaNumeroTSEAndSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndSecaoPleitoPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    Optional<BoletimUrna> findBySecaoPleitoSecaoNumeroTSEAndSecaoPleitoSecaoZonaNumeroTSEAndSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndSecaoPleitoPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    List<BoletimUrna> findByFaseNomeEqualsIgnoreCase(String nomeFase);

    List<BoletimUrna> findByOrigemNomeAbreviadoEqualsIgnoreCase(String nomeAbreviadoOrigemBoletimUrna);

    List<BoletimUrna> findByUrnaEletronicaNumeroSerie(Integer numeroSerieUrnaEletronica);

    @Modifying
    @Transactional
    void deleteBySecaoPleitoSecaoNumeroTSEAndSecaoPleitoSecaoZonaNumeroTSEAndSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndSecaoPleitoPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByFaseNomeEqualsIgnoreCase(String nomeFase);

    @Modifying
    @Transactional
    void deleteByOrigemNomeAbreviadoEqualsIgnoreCase(String nomeAbreviadoOrigemBoletimUrna);

    @Modifying
    @Transactional
    void deleteByUrnaEletronicaNumeroSerie(Integer numeroSerieUrnaEletronica);
}
