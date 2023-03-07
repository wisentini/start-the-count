package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PartidoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Partido;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PartidoMapper {

    public PartidoRetrievalDTO toPartidoRetrievalDTO(Partido partido) {
        return new PartidoRetrievalDTO(
            partido.getId(),
            partido.getNumeroTSE(),
            partido.getNome(),
            partido.getSigla()
        );
    }
}
