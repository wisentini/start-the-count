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
    "id", "partido", "boletimUrna", "cargoEleicao",
    "quantidadeVotosDeLegenda", "totalVotosApurados"
})
public class ApuracaoVotosPartidoBoletimUrnaRetrievalDTO {

    private Integer id;

    private PartidoRetrievalDTO partido;

    private BoletimUrnaRetrievalDTO boletimUrna;

    private CargoEleicaoRetrievalDTO cargoEleicao;

    private Integer quantidadeVotosDeLegenda;

    private Integer totalVotosApurados;
}
