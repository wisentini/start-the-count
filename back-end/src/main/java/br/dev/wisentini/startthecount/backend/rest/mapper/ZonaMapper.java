package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ZonaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ZonaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Zona;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZonaMapper {

    private final UFMapper ufMapper;

    public ZonaRetrievalDTO toZonaRetrievalDTO(Zona zona) {
        return new ZonaRetrievalDTO(
            zona.getId(),
            zona.getNumeroTSE(),
            this.ufMapper.toUFRetrievalDTO(zona.getUF())
        );
    }

    public ZonaIdDTO toZonaIdDTO(Zona zona) {
        return new ZonaIdDTO(
            zona.getNumeroTSE(),
            zona.getUF().getSigla()
        );
    }
}
