package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    "id", "secaoPleito", "origem", "urnaEletronica", "assinatura",
    "quantidadeTotalQRCodes", "dataEmissao", "horarioEmissao"
})
public class BoletimUrnaRetrievalDTO {

    private Integer id;

    private SecaoPleitoRetrievalDTO secaoPleito;

    private FaseRetrievalDTO fase;

    private OrigemBoletimUrnaRetrievalDTO origem;

    private UrnaEletronicaRetrievalDTO urnaEletronica;

    private String assinatura;

    private Integer quantidadeTotalQRCodes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataEmissao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime horarioEmissao;
}
