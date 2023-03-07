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
@JsonPropertyOrder(value = {"id", "papel", "permissao"})
public class PapelPermissaoRetrievalDTO {

    private Integer id;

    private PapelRetrievalDTO papel;

    private PermissaoRetrievalDTO permissao;
}
