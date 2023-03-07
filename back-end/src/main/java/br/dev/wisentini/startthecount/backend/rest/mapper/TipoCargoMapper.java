package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.TipoCargoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.TipoCargo;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipoCargoMapper {

    public TipoCargoRetrievalDTO toTipoCargoRetrievalDTO(TipoCargo tipoCargo) {
        return new TipoCargoRetrievalDTO(
            tipoCargo.getId(),
            tipoCargo.getCodigoTSE(),
            tipoCargo.getNome()
        );
    }
}
