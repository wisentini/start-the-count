package br.dev.wisentini.startthecount.backend.rest.dto.id;

import br.dev.wisentini.startthecount.backend.rest.exception.CampoFaltanteException;
import br.dev.wisentini.startthecount.backend.rest.exception.CampoInvalidoException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SecaoIdDTO {

    @NotNull(message = "O número da seção deve ser informado para identificar uma seção.")
    private Integer numeroTSESecao;

    @NotNull(message = "O número da zona deve ser informado para identificar uma seção.")
    private Integer numeroTSEZona;

    @NotBlank(message = "A sigla da UF deve ser informada para identificar uma seção.")
    @Size(min = 2, max = 2, message = "A sigla da UF deve possuir 2 caracteres.")
    private String siglaUF;

    public void validate() {
        if (Objects.isNull(this.numeroTSESecao)) {
            throw new CampoFaltanteException("O número da seção deve ser informado para identificar uma seção.");
        }

        if (Objects.isNull(this.numeroTSEZona)) {
            throw new CampoFaltanteException("O número da zona deve ser informado para identificar uma seção.");
        }

        if (StringUtils.isBlank(this.siglaUF)) {
            throw new CampoFaltanteException("A sigla da UF deve ser informada para identificar uma seção.");
        }

        if (this.siglaUF.length() != 2) {
            throw new CampoInvalidoException("A sigla da UF deve possuir 2 caracteres.");
        }
    }
}
