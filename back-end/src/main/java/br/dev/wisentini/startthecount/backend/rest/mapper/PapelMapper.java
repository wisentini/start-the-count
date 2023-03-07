package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PapelRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Papel;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PapelMapper {

    public PapelRetrievalDTO toPapelRetrievalDTO(Papel papel) {
        return new PapelRetrievalDTO(
            papel.getId(),
            papel.getNome(),
            papel.getDescricao()
        );
    }
}
