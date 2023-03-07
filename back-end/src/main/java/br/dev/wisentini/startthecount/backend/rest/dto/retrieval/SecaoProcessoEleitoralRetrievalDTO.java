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
@JsonPropertyOrder(value = {"id", "secao", "processoEleitoral", "localVotacao"})
public class SecaoProcessoEleitoralRetrievalDTO {

    private Integer id;

    private SecaoRetrievalDTO secao;

    private ProcessoEleitoralRetrievalDTO processoEleitoral;

    private LocalVotacaoRetrievalDTO localVotacao;
}
