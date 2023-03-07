package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UFRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.UF;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UFMapper {

    private final RegiaoMapper regiaoMapper;

    public UFRetrievalDTO toUFRetrievalDTO(UF uf) {
        return new UFRetrievalDTO(
            uf.getId(),
            uf.getCodigoIBGE(),
            uf.getNome(),
            uf.getSigla(),
            this.regiaoMapper.toRegiaoRetrievalDTO(uf.getRegiao())
        );
    }
}
