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
public class LocalVotacaoIdDTO {

    @NotNull(message = "O número do local de votação deve ser informado para identificar um local de votação.")
    private Integer numeroTSELocalVotacao;

    @NotNull(message = "O número da zona deve ser informado para identificar um local de votação.")
    private Integer numeroTSEZona;

    @NotBlank(message = "A sigla da UF deve ser informada para identificar um local de votação.")
    @Size(min = 2, max = 2, message = "A sigla da UF deve possuir 2 caracteres.")
    private String siglaUF;

    public void validate() {
        if (Objects.isNull(this.numeroTSELocalVotacao)) {
            throw new CampoFaltanteException("O número do local de votação deve ser informado para identificar um local de votação.");
        }

        if (Objects.isNull(this.numeroTSEZona)) {
            throw new CampoFaltanteException("O número da zona deve ser informado para identificar um local de votação.");
        }

        if (StringUtils.isBlank(this.siglaUF)) {
            throw new CampoFaltanteException("A sigla da UF deve ser informada para identificar um local de votação.");
        }

        if (this.siglaUF.length() != 2) {
            throw new CampoInvalidoException("A sigla da UF deve possuir 2 caracteres.");
        }
    }
}
