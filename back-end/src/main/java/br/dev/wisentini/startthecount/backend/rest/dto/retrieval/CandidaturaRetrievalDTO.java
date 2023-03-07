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
@JsonPropertyOrder(value = {"id", "candidato", "cargoEleicao", "partido"})
public class CandidaturaRetrievalDTO {

    private Integer id;

    private CandidatoRetrievalDTO candidato;

    private CargoEleicaoRetrievalDTO cargoEleicao;

    private PartidoRetrievalDTO partido;
}
