package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import br.dev.wisentini.startthecount.backend.rest.model.UF;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"id", "numeroTSE", "uf"})
public class ZonaRetrievalDTO {

    private Integer id;

    private Integer numeroTSE;

    private UF uf;

    public UF getUF() {
        return this.uf;
    }
}
