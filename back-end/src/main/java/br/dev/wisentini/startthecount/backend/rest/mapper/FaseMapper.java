package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.FaseRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Fase;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FaseMapper {

    public FaseRetrievalDTO toFaseRetrievalDTO(Fase fase) {
        return new FaseRetrievalDTO(
            fase.getId(),
            fase.getCodigoTSE(),
            fase.getNome()
        );
    }
}
