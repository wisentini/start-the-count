package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

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
@JsonPropertyOrder(value = {"id", "numeroTSE", "zona"})
public class SecaoRetrievalDTO {

    private Integer id;

    private Integer numeroTSE;

    @JsonProperty(value = "zona")
    private ZonaRetrievalDTO zonaRetrievalDTO;
}
