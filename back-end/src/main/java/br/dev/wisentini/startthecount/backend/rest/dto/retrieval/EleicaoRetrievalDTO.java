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
@JsonPropertyOrder(value = {
    "id", "codigoTSE", "nome", "ano", "quantidadeVotosCargosMajoritarios",
    "quantidadeVotosCargosProporcionais", "pleito"
})
public class EleicaoRetrievalDTO {

    private Integer id;

    private Integer codigoTSE;

    private String nome;

    private Integer ano;

    private Integer quantidadeVotosCargosMajoritarios;

    private Integer quantidadeVotosCargosProporcionais;

    private PleitoRetrievalDTO pleito;
}
