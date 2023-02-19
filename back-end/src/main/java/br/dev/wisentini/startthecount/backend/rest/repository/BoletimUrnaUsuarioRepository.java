package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.BoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.model.BoletimUrnaUsuario;
import br.dev.wisentini.startthecount.backend.rest.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BoletimUrnaUsuarioRepository extends JpaRepository<BoletimUrnaUsuario, Integer> {

    boolean existsByUsuarioUsernameEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(String username, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    Optional<BoletimUrnaUsuario> findByUsuarioUsernameEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(String username, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Query(
        value = """
            SELECT buu.usuario
            FROM BoletimUrnaUsuario AS buu
            WHERE
                buu.boletimUrna.secaoPleito.secao.numeroTSE = :numeroTSESecao AND
                buu.boletimUrna.secaoPleito.secao.zona.numeroTSE = :numeroTSEZona AND
                buu.boletimUrna.secaoPleito.secao.zona.uf.sigla ILIKE :siglaUF AND
                buu.boletimUrna.secaoPleito.pleito.codigoTSE = :codigoTSEPleito
        """
    )
    List<Usuario> findUsuariosByBoletimUrna(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Query(value = "SELECT buu.boletimUrna FROM BoletimUrnaUsuario AS buu WHERE buu.usuario.username ILIKE :username")
    List<BoletimUrna> findBoletinsUrnaByUsuario(String username);

    @Modifying
    @Transactional
    void deleteByUsuarioUsernameEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(String username, Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(Integer numeroTSESecao, Integer numeroTSEZona, String siglaUF, Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByUsuarioUsernameEqualsIgnoreCase(String username);
}
