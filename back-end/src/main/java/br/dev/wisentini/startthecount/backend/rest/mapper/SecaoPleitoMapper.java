package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoPleitoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.SecaoPleitoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoPleito;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecaoPleitoMapper {

    private final SecaoMapper secaoMapper;

    public SecaoPleitoRetrievalDTO toSecaoPleitoRetrievalDTO(SecaoPleito secaoPleito) {
        return new SecaoPleitoRetrievalDTO(
            secaoPleito.getId(),
            this.secaoMapper.toSecaoRetrievalDTO(secaoPleito.getSecao()),
            secaoPleito.getPleito()
        );
    }

    public SecaoPleitoIdDTO toSecaoPleitoIdDTO(SecaoPleito secaoPleito) {
        return new SecaoPleitoIdDTO(
            secaoPleito.getSecao().getNumeroTSE(),
            secaoPleito.getSecao().getZona().getNumeroTSE(),
            secaoPleito.getSecao().getZona().getUF().getSigla(),
            secaoPleito.getPleito().getCodigoTSE()
        );
    }
}
