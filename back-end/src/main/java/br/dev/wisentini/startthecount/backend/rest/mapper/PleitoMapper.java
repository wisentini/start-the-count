package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PleitoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Pleito;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PleitoMapper {

    private final ProcessoEleitoralMapper processoEleitoralMapper;

    public PleitoRetrievalDTO toPleitoRetrievalDTO(Pleito pleito) {
        return new PleitoRetrievalDTO(
            pleito.getId(),
            pleito.getCodigoTSE(),
            pleito.getNome(),
            pleito.getTurno(),
            pleito.getData(),
            this.processoEleitoralMapper.toProcessoEleitoralRetrievalDTO(pleito.getProcessoEleitoral())
        );
    }
}
