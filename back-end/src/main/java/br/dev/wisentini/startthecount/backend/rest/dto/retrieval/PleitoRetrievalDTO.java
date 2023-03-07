package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"id", "codigoTSE", "nome", "turno", "data", "processoEleitoral"})
public class PleitoRetrievalDTO {

    private Integer id;

    private Integer codigoTSE;

    private String nome;

    private Integer turno;

    private LocalDate data;

    private ProcessoEleitoralRetrievalDTO processoEleitoral;
}
