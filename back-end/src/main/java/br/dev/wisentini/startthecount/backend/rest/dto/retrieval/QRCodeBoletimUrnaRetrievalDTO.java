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
@JsonPropertyOrder(value = {"id", "boletimUrna", "cabecalho", "conteudo", "hash", "indice", "numeroCiclosEleitoraisDesdeImplementacao", "numeroRevisoesFormatoCiclo", "numeroVersaoChaveAssinatura"})
public class QRCodeBoletimUrnaRetrievalDTO {

    private Integer id;

    @JsonProperty(value = "boletimUrna")
    private BoletimUrnaRetrievalDTO boletimUrnaRetrievalDTO;

    private String cabecalho;

    private String conteudo;

    private String hash;

    private Integer indice;

    private Integer numeroCiclosEleitoraisDesdeImplementacao;

    private Integer numeroRevisoesFormatoCiclo;

    private Integer numeroVersaoChaveAssinatura;
}
