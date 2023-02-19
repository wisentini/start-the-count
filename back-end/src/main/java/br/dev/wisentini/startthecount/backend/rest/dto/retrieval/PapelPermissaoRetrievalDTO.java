package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import br.dev.wisentini.startthecount.backend.rest.model.Permissao;
import br.dev.wisentini.startthecount.backend.rest.model.Papel;

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

    private Papel papel;

    private Permissao permissao;
}
