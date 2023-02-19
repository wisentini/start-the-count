package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import br.dev.wisentini.startthecount.backend.rest.model.Partido;

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
@JsonPropertyOrder(value = {"id", "partido", "boletimUrna", "cargoEleicao", "quantidadeVotosDeLegenda", "totalVotosApurados"})
public class ApuracaoVotosPartidoBoletimUrnaRetrievalDTO {

    private Integer id;

    private Partido partido;

    @JsonProperty(value = "boletimUrna")
    private BoletimUrnaRetrievalDTO boletimUrnaRetrievalDTO;

    @JsonProperty(value = "cargoEleicao")
    private CargoEleicaoRetrievalDTO cargoEleicaoRetrievalDTO;

    private Integer quantidadeVotosDeLegenda;

    private Integer totalVotosApurados;
}
