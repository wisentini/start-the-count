package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCargoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.ApuracaoVotosCargoBoletimUrna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ApuracaoVotosCargoBoletimUrnaRepository extends JpaRepository<ApuracaoVotosCargoBoletimUrna, Integer> {

    boolean existsByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    Optional<ApuracaoVotosCargoBoletimUrna> findByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Query(
        value = """
            SELECT
                cod_tse_cargo AS "codigoCargo",
                cargo.nome AS "nomeCargo",
                cod_tse_eleicao AS "codigoEleicao",
                eleicao.nome AS "nomeEleicao",
                cod_tse_pleito AS "codigoPleito",
                turno,
                SUM(qtde_votos_nominais) AS "quantidadeVotosNominais",
                SUM(qtde_votos_de_legenda) AS "quantidadeVotosDeLegenda",
                SUM(qtde_votos_em_branco) AS "quantidadeVotosEmBranco",
                SUM(qtde_votos_nulos) AS "quantidadeVotosNulos",
                SUM(total_votos_apurados) AS "totalVotosApurados"
            FROM
                apuracao_votos_cargo_boletim_urna NATURAL JOIN
                cargo_eleicao NATURAL JOIN
                cargo INNER JOIN
                eleicao USING (id_eleicao) INNER JOIN
                pleito USING (id_pleito)
            GROUP BY "codigoCargo", "nomeCargo", "codigoEleicao", "nomeEleicao", "codigoPleito", turno
            ORDER BY "totalVotosApurados" DESC;
        """,
        nativeQuery = true
    )
    List<ApuracaoVotosCargoRetrievalDTO> findApuracaoVotosCargos();

    @Modifying
    @Transactional
    void deleteByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao);
}
