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
@ToString(doNotUseGetters = true)
public class AgregacaoSecaoIdDTO {

    @NotNull(message = "O número da seção principal deve ser informado para identificar uma agregação de seção.")
    private Integer numeroTSESecaoPrincipal;

    @NotNull(message = "O número da zona da seção principal deve ser informado para identificar uma agregação de seção.")
    private Integer numeroTSEZonaSecaoPrincipal;

    @NotBlank(message = "A sigla da UF da seção principal deve ser informada para identificar uma agregação de seção.")
    @Size(min = 2, max = 2, message = "A sigla da UF da seção principal deve possuir 2 caracteres.")
    private String siglaUFSecaoPrincipal;

    @NotNull(message = "O número da seção agregada deve ser informado para identificar uma agregação de seção.")
    private Integer numeroTSESecaoAgregada;

    @NotNull(message = "O número da zona da seção agregada deve ser informado para identificar uma agregação de seção.")
    private Integer numeroTSEZonaSecaoAgregada;

    @NotBlank(message = "A sigla da UF da seção agregada deve ser informada para identificar uma agregação de seção.")
    @Size(min = 2, max = 2, message = "A sigla da UF da seção agregada deve possuir 2 caracteres.")
    private String siglaUFSecaoAgregada;

    @NotNull(message = "O código do processo eleitoral deve ser informado para identificar uma agregação de seção.")
    private Integer codigoTSEProcessoEleitoral;

    public void validate() {
        if (Objects.isNull(this.numeroTSESecaoPrincipal)) {
            throw new CampoFaltanteException("O número da seção principal deve ser informado para identificar uma agregação de seção.");
        }

        if (Objects.isNull(this.numeroTSEZonaSecaoPrincipal)) {
            throw new CampoFaltanteException("O número da zona da seção principal deve ser informado para identificar uma agregação de seção.");
        }

        if (StringUtils.isBlank(this.siglaUFSecaoPrincipal)) {
            throw new CampoFaltanteException("A sigla da UF da seção principal deve ser informada para identificar uma agregação de seção.");
        }

        if (this.siglaUFSecaoPrincipal.length() != 2) {
            throw new CampoInvalidoException("A sigla da UF da seção principal deve possuir 2 caracteres.");
        }

        if (Objects.isNull(this.numeroTSESecaoAgregada)) {
            throw new CampoFaltanteException("O número da seção agregada deve ser informado para identificar uma agregação de seção.");
        }

        if (Objects.isNull(this.numeroTSEZonaSecaoAgregada)) {
            throw new CampoFaltanteException("O número da zona da seção agregada deve ser informado para identificar uma agregação de seção.");
        }

        if (StringUtils.isBlank(this.siglaUFSecaoAgregada)) {
            throw new CampoFaltanteException("A sigla da UF da seção agregada deve ser informada para identificar uma agregação de seção.");
        }

        if (this.siglaUFSecaoAgregada.length() != 2) {
            throw new CampoInvalidoException("A sigla da UF da seção agregada deve possuir 2 caracteres.");
        }

        if (Objects.isNull(this.codigoTSEProcessoEleitoral)) {
            throw new CampoFaltanteException("O código do processo eleitoral deve ser informado para identificar uma agregação de seção.");
        }
    }
}
