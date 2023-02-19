package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import br.dev.wisentini.startthecount.backend.rest.model.Fase;
import br.dev.wisentini.startthecount.backend.rest.model.OrigemBoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.model.UrnaEletronica;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonPropertyOrder(value = {"id", "secaoPleito", "origem", "urnaEletronica", "assinatura", "quantidadeTotalQRCodes", "dataEmissao", "horarioEmissao"})
public class BoletimUrnaRetrievalDTO {

    private Integer id;

    @JsonProperty(value = "secaoPleito")
    private SecaoPleitoRetrievalDTO secaoPleitoRetrievalDTO;

    private Fase fase;

    private OrigemBoletimUrna origem;

    private UrnaEletronica urnaEletronica;

    private String assinatura;

    private Integer quantidadeTotalQRCodes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataEmissao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime horarioEmissao;
}
