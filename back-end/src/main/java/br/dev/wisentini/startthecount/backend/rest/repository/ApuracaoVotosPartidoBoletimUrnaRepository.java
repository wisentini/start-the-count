package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosPartidoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.ApuracaoVotosPartidoBoletimUrna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ApuracaoVotosPartidoBoletimUrnaRepository extends JpaRepository<ApuracaoVotosPartidoBoletimUrna, Integer> {

    boolean existsByPartidoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSEPartido, Integer codigoTSECargo, Integer codigoTSEEleicao, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    Optional<ApuracaoVotosPartidoBoletimUrna> findByPartidoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSEPartido, Integer codigoTSECargo, Integer codigoTSEEleicao, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Query(
        value = """
            SELECT
                num_tse_partido AS "numeroPartido",
                partido.nome AS "nomePartido",
                cod_tse_cargo AS "codigoCargo",
                cargo.nome AS "nomeCargo",
                cod_tse_eleicao AS "codigoEleicao",
                eleicao.nome AS "nomeEleicao",
                cod_tse_pleito AS "codigoPleito",
                turno,
                SUM(qtde_votos_de_legenda) AS "quantidadeVotosDeLegenda",
                SUM(total_votos_apurados) AS "totalVotosApurados"
            FROM
                apuracao_votos_partido_boletim_urna NATURAL JOIN
                partido NATURAL JOIN
                cargo_eleicao INNER JOIN
                cargo USING (id_cargo) INNER JOIN
                eleicao USING (id_eleicao) INNER JOIN
                pleito USING (id_pleito)
            GROUP BY "numeroPartido", "nomePartido", "codigoCargo", "nomeCargo", "codigoEleicao", "nomeEleicao", "codigoPleito", turno
            ORDER BY "numeroPartido", "totalVotosApurados" DESC;
        """,
        nativeQuery = true
    )
    List<ApuracaoVotosPartidoRetrievalDTO> findApuracaoVotosPartidos();

    @Modifying
    @Transactional
    void deleteByPartidoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSEPartido, Integer codigoTSECargo, Integer codigoTSEEleicao, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByPartidoNumeroTSE(Integer numeroTSEPartido);

    @Modifying
    @Transactional
    void deleteByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao);
}
