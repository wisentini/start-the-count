package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Pleito;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoPleito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SecaoPleitoRepository extends JpaRepository<SecaoPleito, Integer> {

    boolean existsBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    Optional<SecaoPleito> findBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    List<SecaoPleito> findBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF);

    List<SecaoPleito> findByPleitoCodigoTSE(Integer codigoTSEPleito);

    @Query(value = "SELECT sp.secao FROM SecaoPleito AS sp WHERE sp.pleito.codigoTSE = :codigoTSEPleito")
    List<Secao> findSecoesByPleito(Integer codigoTSEPleito);

    @Query(
        value = """
            SELECT sp.pleito
            FROM SecaoPleito AS sp
            WHERE
                sp.secao.numeroTSE = :numeroTSESecao AND
                sp.secao.zona.numeroTSE = :numeroTSEZona AND
                sp.secao.zona.uf.sigla ILIKE :siglaUF
        """
    )
    List<Pleito> findPleitosBySecao(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF);

    @Modifying
    @Transactional
    void deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF);

    @Modifying
    @Transactional
    void deleteByPleitoCodigoTSE(Integer codigoTSEPleito);
}
