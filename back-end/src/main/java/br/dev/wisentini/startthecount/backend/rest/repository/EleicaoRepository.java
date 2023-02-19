package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EleicaoRepository extends JpaRepository<Eleicao, Integer> {

    boolean existsByCodigoTSE(Integer codigoTSE);

    Optional<Eleicao> findByCodigoTSE(Integer codigoTSE);

    List<Eleicao> findByPleitoCodigoTSE(Integer codigoTSEPleito);

    @Modifying
    @Transactional
    void deleteByCodigoTSE(Integer codigoTSE);

    @Modifying
    @Transactional
    void deleteByPleitoCodigoTSE(Integer codigoTSEPleito);
}
