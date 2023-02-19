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
@JsonPropertyOrder(value = {"id", "cargo", "boletimUrna", "totalVotosApurados"})
public class ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO {

    private Integer id;

    @JsonProperty(value = "candidatura")
    private CandidaturaRetrievalDTO candidaturaRetrievalDTO;

    @JsonProperty(value = "boletimUrna")
    private BoletimUrnaRetrievalDTO boletimUrnaRetrievalDTO;

    private Integer totalVotosApurados;
}
