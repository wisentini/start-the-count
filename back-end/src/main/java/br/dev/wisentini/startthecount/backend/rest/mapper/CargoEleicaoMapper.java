package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.CargoEleicaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CargoEleicaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.CargoEleicao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CargoEleicaoMapper {

    private final CargoMapper cargoMapper;

    private final EleicaoMapper eleicaoMapper;

    public CargoEleicaoRetrievalDTO toCargoEleicaoRetrievalDTO(CargoEleicao cargoEleicao) {
        return new CargoEleicaoRetrievalDTO(
            cargoEleicao.getId(),
            this.cargoMapper.toCargoRetrievalDTO(cargoEleicao.getCargo()),
            this.eleicaoMapper.toEleicaoRetrievalDTO(cargoEleicao.getEleicao())
        );
    }

    public CargoEleicaoIdDTO toCargoEleicaoIdDTO(CargoEleicao cargoEleicao) {
        return new CargoEleicaoIdDTO(
            cargoEleicao.getCargo().getCodigoTSE(),
            cargoEleicao.getEleicao().getCodigoTSE()
        );
    }
}
