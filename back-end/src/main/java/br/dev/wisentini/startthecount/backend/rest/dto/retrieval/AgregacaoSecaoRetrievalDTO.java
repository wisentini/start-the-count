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
@JsonPropertyOrder(value = {"id", "secaoPrincipal", "secaoAgregada", "processoEleitoral"})
public class AgregacaoSecaoRetrievalDTO {

    private Integer id;

    private SecaoRetrievalDTO secaoPrincipal;

    private SecaoRetrievalDTO secaoAgregada;

    private ProcessoEleitoralRetrievalDTO processoEleitoral;
}
