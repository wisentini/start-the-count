package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecaoMapper {

    private final ZonaMapper zonaMapper;

    public SecaoRetrievalDTO toSecaoRetrievalDTO(Secao secao) {
        return new SecaoRetrievalDTO(
            secao.getId(),
            secao.getNumeroTSE(),
            this.zonaMapper.toZonaRetrievalDTO(secao.getZona())
        );
    }

    public SecaoIdDTO toSecaoIdDTO(Secao secao) {
        return new SecaoIdDTO(
            secao.getNumeroTSE(),
            secao.getZona().getNumeroTSE(),
            secao.getZona().getUF().getSigla()
        );
    }
}
