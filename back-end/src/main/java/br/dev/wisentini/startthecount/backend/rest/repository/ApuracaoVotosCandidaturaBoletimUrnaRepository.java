package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCandidaturaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.ApuracaoVotosCandidaturaBoletimUrna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ApuracaoVotosCandidaturaBoletimUrnaRepository extends JpaRepository<ApuracaoVotosCandidaturaBoletimUrna, Integer> {

    boolean existsByCandidaturaCandidatoNumeroTSEAndCandidaturaCargoEleicaoCargoCodigoTSEAndCandidaturaCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSECandidato, Integer codigoTSECargo, Integer codigoTSEEleicao, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    Optional<ApuracaoVotosCandidaturaBoletimUrna> findByCandidaturaCandidatoNumeroTSEAndCandidaturaCargoEleicaoCargoCodigoTSEAndCandidaturaCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSECandidato, Integer codigoTSECargo, Integer codigoTSEEleicao, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Query(
        value = """
            SELECT
                num_tse_candidato AS "numeroCandidato",
                candidato.nome AS "nomeCandidato",
                cod_tse_cargo AS "codigoCargo",
                cargo.nome AS "nomeCargo",
                cod_tse_eleicao AS "codigoEleicao",
                eleicao.nome AS "nomeEleicao",
                cod_tse_pleito AS "codigoPleito",
                turno,
                SUM(total_votos_apurados) AS "totalVotosApurados"
            FROM
                apuracao_votos_candidatura_boletim_urna NATURAL JOIN
                candidatura NATURAL JOIN
                candidato NATURAL JOIN
                cargo_eleicao INNER JOIN
                cargo USING (id_cargo) INNER JOIN
                eleicao USING (id_eleicao) INNER JOIN
                pleito USING (id_pleito)
            GROUP BY "numeroCandidato", "nomeCandidato", "codigoCargo", "nomeCargo", "codigoEleicao", "nomeEleicao", "codigoPleito", turno
            ORDER BY "totalVotosApurados" DESC;
        """,
        nativeQuery = true
    )
    List<ApuracaoVotosCandidaturaRetrievalDTO> findApuracaoVotosCandidaturas();

    @Modifying
    @Transactional
    void deleteByCandidaturaCandidatoNumeroTSEAndCandidaturaCargoEleicaoCargoCodigoTSEAndCandidaturaCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSECandidato, Integer codigoTSECargo, Integer codigoTSEEleicao, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByCandidaturaCandidatoNumeroTSEAndCandidaturaCargoEleicaoCargoCodigoTSEAndCandidaturaCargoEleicaoEleicaoCodigoTSE(Integer numeroTSECandidato, Integer codigoTSECargo, Integer codigoTSEEleicao);
}
