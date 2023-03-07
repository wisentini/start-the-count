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
    "id", "cargoEleicao", "boletimUrna", "quantidadeEleitoresAptos",
    "quantidadeComparecimentoCargoSemCandidatos", "quantidadeVotosNominais",
    "quantidadeVotosDeLegenda", "quantidadeVotosEmBranco", "quantidadeVotosNulos", "totalVotosApurados"
})
public class ApuracaoVotosCargoBoletimUrnaRetrievalDTO {

    private Integer id;

    private CargoEleicaoRetrievalDTO cargoEleicao;

    private BoletimUrnaRetrievalDTO boletimUrna;

    private Integer quantidadeEleitoresAptos;

    private Integer quantidadeComparecimentoCargoSemCandidatos;

    private Integer quantidadeVotosNominais;

    private Integer quantidadeVotosDeLegenda;

    private Integer quantidadeVotosEmBranco;

    private Integer quantidadeVotosNulos;

    private Integer totalVotosApurados;
}
