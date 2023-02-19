package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.BoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.BoletimUrna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BoletimUrnaMapper {

    private final SecaoPleitoMapper secaoPleitoMapper;

    @Autowired
    public BoletimUrnaMapper(@Lazy SecaoPleitoMapper secaoPleitoMapper) {
        this.secaoPleitoMapper = secaoPleitoMapper;
    }

    public BoletimUrnaRetrievalDTO toBoletimUrnaRetrievalDTO(BoletimUrna boletimUrna) {
        return new BoletimUrnaRetrievalDTO(
            boletimUrna.getId(),
            this.secaoPleitoMapper.toSecaoPleitoRetrievalDTO(boletimUrna.getSecaoPleito()),
            boletimUrna.getFase(),
            boletimUrna.getOrigem(),
            boletimUrna.getUrnaEletronica(),
            boletimUrna.getAssinatura(),
            boletimUrna.getQuantidadeTotalQRCodes(),
            boletimUrna.getDataEmissao(),
            boletimUrna.getHorarioEmissao()
        );
    }

    public BoletimUrnaIdDTO toBoletimUrnaIdDTO(BoletimUrna boletimUrna) {
        return new BoletimUrnaIdDTO(
            boletimUrna.getSecaoPleito().getSecao().getNumeroTSE(),
            boletimUrna.getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            boletimUrna.getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            boletimUrna.getSecaoPleito().getPleito().getCodigoTSE()
        );
    }
}
