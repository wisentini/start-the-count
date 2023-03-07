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
@JsonPropertyOrder(value = {"id", "candidatura", "boletimUrna", "totalVotosApurados"})
public class ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO {

    private Integer id;

    private CandidaturaRetrievalDTO candidatura;

    private BoletimUrnaRetrievalDTO boletimUrna;

    private Integer totalVotosApurados;
}
