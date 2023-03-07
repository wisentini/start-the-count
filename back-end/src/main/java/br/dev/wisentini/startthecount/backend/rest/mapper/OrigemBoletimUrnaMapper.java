package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.OrigemBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.OrigemBoletimUrna;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrigemBoletimUrnaMapper {

    public OrigemBoletimUrnaRetrievalDTO toOrigemBoletimUrnaRetrievalDTO(OrigemBoletimUrna origemBoletimUrna) {
        return new OrigemBoletimUrnaRetrievalDTO(
            origemBoletimUrna.getId(),
            origemBoletimUrna.getNome(),
            origemBoletimUrna.getNomeAbreviado()
        );
    }
}
