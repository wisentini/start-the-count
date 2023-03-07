package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.RegiaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Regiao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegiaoMapper {

    public RegiaoRetrievalDTO toRegiaoRetrievalDTO(Regiao regiao) {
        return new RegiaoRetrievalDTO(
            regiao.getId(),
            regiao.getCodigoIBGE(),
            regiao.getNome(),
            regiao.getSigla()
        );
    }
}
