package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.LocalVotacaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.LocalVotacaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.LocalVotacao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalVotacaoMapper {

    private final ZonaMapper zonaMapper;

    public LocalVotacaoRetrievalDTO toLocalVotacaoRetrievalDTO(LocalVotacao localVotacao) {
        return new LocalVotacaoRetrievalDTO(
            localVotacao.getId(),
            localVotacao.getNumeroTSE(),
            localVotacao.getNome(),
            this.zonaMapper.toZonaRetrievalDTO(localVotacao.getZona()),
            localVotacao.getMunicipio()
        );
    }

    public LocalVotacaoIdDTO toLocalVotacaoIdDTO(LocalVotacao localVotacao) {
        return new LocalVotacaoIdDTO(
            localVotacao.getNumeroTSE(),
            localVotacao.getZona().getNumeroTSE(),
            localVotacao.getZona().getUF().getSigla()
        );
    }
}
