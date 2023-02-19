package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import br.dev.wisentini.startthecount.backend.rest.model.Pleito;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"id", "secao", "pleito"})
public class SecaoPleitoRetrievalDTO {

    private Integer id;

    @JsonProperty(value = "secao")
    private SecaoRetrievalDTO secaoRetrievalDTO;

    private Pleito pleito;
}
