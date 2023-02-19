package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Cargo;
import br.dev.wisentini.startthecount.backend.rest.model.CargoEleicao;
import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CargoEleicaoRepository extends JpaRepository<CargoEleicao, Integer> {

    boolean existsByCargoCodigoTSEAndEleicaoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao);

    Optional<CargoEleicao> findByCargoCodigoTSEAndEleicaoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao);

    @Query(value = "SELECT ce.cargo FROM CargoEleicao AS ce WHERE ce.eleicao.codigoTSE = :codigoTSEEleicao")
    List<Cargo> findCargosByEleicao(Integer codigoTSEEleicao);

    @Query(value = "SELECT ce.eleicao FROM CargoEleicao AS ce WHERE ce.cargo.codigoTSE = :codigoTSECargo")
    List<Eleicao> findEleicoesByCargo(Integer codigoTSECargo);

    List<CargoEleicao> findByCargoCodigoTSE(Integer codigoTSECargo);

    List<CargoEleicao> findByEleicaoCodigoTSE(Integer codigoTSEEleicao);

    @Modifying
    @Transactional
    void deleteByCargoCodigoTSEAndEleicaoCodigoTSE(Integer codigoTSECargo, Integer codigoTSEEleicao);

    @Modifying
    @Transactional
    void deleteByCargoCodigoTSE(Integer codigoTSECargo);

    @Modifying
    @Transactional
    void deleteByEleicaoCodigoTSE(Integer codigoTSEEleicao);
}
