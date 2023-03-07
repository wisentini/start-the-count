package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {
    "id", "numeroSerie", "codigoIdentificacaoCarga",
    "versaoSoftware", "dataAbertura", "horarioAbertura",
    "dataFechamento", "horarioFechamento"
})
public class UrnaEletronicaRetrievalDTO {

    private Integer id;

    private Integer numeroSerie;

    private String codigoIdentificacaoCarga;

    private String versaoSoftware;

    private LocalDate dataAbertura;

    private LocalTime horarioAbertura;

    private LocalDate dataFechamento;

    private LocalTime horarioFechamento;
}
