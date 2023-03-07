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
@JsonPropertyOrder(value = {"id", "nome", "nomeAbreviado"})
public class OrigemConfiguracaoProcessoEleitoralRetrievalDTO {

    private Integer id;

    private String nome;

    private String nomeAbreviado;
}
