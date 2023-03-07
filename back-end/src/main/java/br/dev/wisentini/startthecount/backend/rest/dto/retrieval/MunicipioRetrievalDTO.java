package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"id", "codigoTSE", "codigoIBGE", "nome", "uf"})
public class MunicipioRetrievalDTO {

    private Integer id;

    private Integer codigoTSE;

    private Integer codigoIBGE;

    private String nome;

    private UFRetrievalDTO uf;
}
