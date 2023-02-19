package br.dev.wisentini.startthecount.backend.rest.dto.id;

import br.dev.wisentini.startthecount.backend.rest.exception.CampoFaltanteException;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CargoEleicaoIdDTO {

    @NotNull(message = "O código do cargo deve ser informado para identificar uma relação entre cargo e eleição.")
    private Integer codigoTSECargo;

    @NotNull(message = "O código da eleição deve ser informado para identificar uma relação entre cargo e eleição.")
    private Integer codigoTSEEleicao;

    public void validate() {
        if (Objects.isNull(this.codigoTSECargo)) {
            throw new CampoFaltanteException("O código do cargo deve ser informado para identificar uma relação entre cargo e eleição.");
        }

        if (Objects.isNull(this.codigoTSEEleicao)) {
            throw new CampoFaltanteException("O código da eleição deve ser informado para identificar uma relação entre cargo e eleição.");
        }
    }
}
