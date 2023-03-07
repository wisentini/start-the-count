package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ProcessoEleitoralRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.ProcessoEleitoral;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessoEleitoralMapper {

    private final OrigemConfiguracaoProcessoEleitoralMapper origemConfiguracaoProcessoEleitoralMapper;

    public ProcessoEleitoralRetrievalDTO toProcessoEleitoralRetrievalDTO(ProcessoEleitoral processoEleitoral) {
        return new ProcessoEleitoralRetrievalDTO(
            processoEleitoral.getId(),
            processoEleitoral.getCodigoTSE(),
            processoEleitoral.getNome(),
            this.origemConfiguracaoProcessoEleitoralMapper.toOrigemConfiguracaoProcessoEleitoralRetrievalDTO(
                processoEleitoral.getOrigemConfiguracao()
            )
        );
    }
}
