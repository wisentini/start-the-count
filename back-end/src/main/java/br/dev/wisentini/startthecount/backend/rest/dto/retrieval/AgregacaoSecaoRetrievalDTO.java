package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import br.dev.wisentini.startthecount.backend.rest.model.ProcessoEleitoral;

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
@JsonPropertyOrder(value = {"id", "secaoPrincipal", "secaoAgregada", "processoEleitoral"})
public class AgregacaoSecaoRetrievalDTO {

    private Integer id;

    @JsonProperty(value = "secaoPrincipal")
    private SecaoRetrievalDTO secaoPrincipalRetrievalDTO;

    @JsonProperty(value = "secaoAgregada")
    private SecaoRetrievalDTO secaoAgregadaRetrievalDTO;

    private ProcessoEleitoral processoEleitoral;
}
