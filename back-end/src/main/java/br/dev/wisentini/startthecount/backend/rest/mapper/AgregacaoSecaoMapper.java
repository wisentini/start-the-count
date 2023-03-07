package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.AgregacaoSecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.AgregacaoSecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.AgregacaoSecao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgregacaoSecaoMapper {

    private final SecaoMapper secaoMapper;

    private final ProcessoEleitoralMapper processoEleitoralMapper;

    public AgregacaoSecaoRetrievalDTO toAgregacaoSecaoRetrievalDTO(AgregacaoSecao agregacaoSecao) {
        return new AgregacaoSecaoRetrievalDTO(
            agregacaoSecao.getId(),
            this.secaoMapper.toSecaoRetrievalDTO(agregacaoSecao.getSecaoPrincipal()),
            this.secaoMapper.toSecaoRetrievalDTO(agregacaoSecao.getSecaoAgregada()),
            this.processoEleitoralMapper.toProcessoEleitoralRetrievalDTO(agregacaoSecao.getProcessoEleitoral())
        );
    }

    public AgregacaoSecaoIdDTO toAgregacaoSecaoIdDTO(AgregacaoSecao agregacaoSecao) {
        return new AgregacaoSecaoIdDTO(
            agregacaoSecao.getSecaoPrincipal().getNumeroTSE(),
            agregacaoSecao.getSecaoPrincipal().getZona().getNumeroTSE(),
            agregacaoSecao.getSecaoPrincipal().getZona().getUF().getSigla(),
            agregacaoSecao.getSecaoAgregada().getNumeroTSE(),
            agregacaoSecao.getSecaoAgregada().getZona().getNumeroTSE(),
            agregacaoSecao.getSecaoAgregada().getZona().getUF().getSigla(),
            agregacaoSecao.getProcessoEleitoral().getCodigoTSE()
        );
    }
}
