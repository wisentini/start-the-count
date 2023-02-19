package br.dev.wisentini.startthecount.backend.core.model;

import br.dev.wisentini.startthecount.backend.util.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class VersaoFormatoRepresentacao {

    private Integer numeroCiclosEleitoraisDesdeImplementacao;

    private Integer numeroRevisoesFormatoCiclo;

    public VersaoFormatoRepresentacao(String string) {
        if (Objects.isNull(string)) {
            throw new NullPointerException(
                "A versão do formato de representação do boletim de urna deve ser informada."
            );
        }

        String[] numeros = string.split("\\.");

        this.numeroCiclosEleitoraisDesdeImplementacao = StringUtil.toInteger(numeros[0]);
        this.numeroRevisoesFormatoCiclo = StringUtil.toInteger(numeros[1]);
    }
}
