package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.ProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoProcessoEleitoral;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SecaoProcessoEleitoralRepository extends JpaRepository<SecaoProcessoEleitoral, Integer> {

    boolean existsBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEProcessoEleitoral);

    Optional<SecaoProcessoEleitoral> findBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEProcessoEleitoral);

    @Query(
        value = """
            SELECT spe.processoEleitoral
            FROM SecaoProcessoEleitoral AS spe
            WHERE
                spe.secao.numeroTSE = :numeroTSESecao AND
                spe.secao.zona.numeroTSE = :numeroTSEZona AND
                spe.secao.zona.uf.sigla ILIKE :siglaUF
        """
    )
    List<ProcessoEleitoral> findProcessosEleitoraisBySecao(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF);

    @Query(value = "SELECT spe.secao FROM SecaoProcessoEleitoral AS spe WHERE spe.processoEleitoral.codigoTSE = :codigoTSEProcessoEleitoral")
    List<Secao> findSecoesByProcessoEleitoral(Integer codigoTSEProcessoEleitoral);

    @Modifying
    @Transactional
    void deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEProcessoEleitoral);

    @Modifying
    @Transactional
    void deleteByLocalVotacaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(Integer numeroTSELocalVotacao, Integer numeroTSEZona, String siglaUF);

    @Modifying
    @Transactional
    void deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF);

    @Modifying
    @Transactional
    void deleteByProcessoEleitoralCodigoTSE(Integer codigoTSEProcessoEleitoral);
}
