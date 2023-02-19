package br.dev.wisentini.startthecount.backend.rest.repository;

import br.dev.wisentini.startthecount.backend.rest.model.Cargo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    boolean existsByCodigoTSE(Integer codigoTSE);

    Optional<Cargo> findByCodigoTSE(Integer codigoTSE);

    List<Cargo> findByTipoCodigoTSE(Integer codigoTSETipoCargo);

    @Modifying
    @Transactional
    void deleteByCodigoTSE(Integer codigoTSE);

    @Modifying
    @Transactional
    void deleteByTipoCodigoTSE(Integer codigoTSETipoCargo);
}
