package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoProcessoEleitoralIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoProcessoEleitoralRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoProcessoEleitoral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class SecaoProcessoEleitoralMapper {

    private final LocalVotacaoMapper localVotacaoMapper;
    private final SecaoMapper secaoMapper;

    @Autowired
    public SecaoProcessoEleitoralMapper(@Lazy LocalVotacaoMapper localVotacaoMapper, SecaoMapper secaoMapper) {
        this.localVotacaoMapper = localVotacaoMapper;
        this.secaoMapper = secaoMapper;
    }

    public SecaoProcessoEleitoralRetrievalDTO toSecaoProcessoEleitoralRetrievalDTO(SecaoProcessoEleitoral secaoProcessoEleitoral) {
        return new SecaoProcessoEleitoralRetrievalDTO(
            secaoProcessoEleitoral.getId(),
            this.localVotacaoMapper.toLocalVotacaoRetrievalDTO(secaoProcessoEleitoral.getLocalVotacao()),
            this.secaoMapper.toSecaoRetrievalDTO(secaoProcessoEleitoral.getSecao()),
            secaoProcessoEleitoral.getProcessoEleitoral()
        );
    }

    public SecaoProcessoEleitoralIdDTO toSecaoProcessoEleitoralIdDTO(SecaoProcessoEleitoral secaoProcessoEleitoral) {
        return new SecaoProcessoEleitoralIdDTO(
            secaoProcessoEleitoral.getSecao().getNumeroTSE(),
            secaoProcessoEleitoral.getSecao().getZona().getNumeroTSE(),
            secaoProcessoEleitoral.getSecao().getZona().getUF().getSigla(),
            secaoProcessoEleitoral.getProcessoEleitoral().getCodigoTSE()
        );
    }
}
