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
@JsonPropertyOrder(value = {"id", "numeroTSE", "codigoTSE", "nome"})
public class CandidatoRetrievalDTO {

    private Integer id;

    private Integer numeroTSE;

    private String codigoTSE;

    private String nome;
}
