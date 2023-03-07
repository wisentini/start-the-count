package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.OrigemConfiguracaoProcessoEleitoralRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.OrigemConfiguracaoProcessoEleitoral;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrigemConfiguracaoProcessoEleitoralMapper {

    public OrigemConfiguracaoProcessoEleitoralRetrievalDTO toOrigemConfiguracaoProcessoEleitoralRetrievalDTO(
        OrigemConfiguracaoProcessoEleitoral origemConfiguracaoProcessoEleitoral
    ) {
        return new OrigemConfiguracaoProcessoEleitoralRetrievalDTO(
            origemConfiguracaoProcessoEleitoral.getId(),
            origemConfiguracaoProcessoEleitoral.getNome(),
            origemConfiguracaoProcessoEleitoral.getNomeAbreviado()
        );
    }
}
