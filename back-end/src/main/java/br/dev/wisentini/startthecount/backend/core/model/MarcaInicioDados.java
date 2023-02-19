package br.dev.wisentini.startthecount.backend.core.model;

import br.dev.wisentini.startthecount.backend.util.StringUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class MarcaInicioDados {

    private Integer indiceQRCode;

    private Integer quantidadeTotalQRCodes;

    public MarcaInicioDados(String string) {
        if (Objects.isNull(string)) {
            throw new NullPointerException(
                "A marca de início dos dados do boletim de urna deve ser informada."
            );
        }

        String[] strings = string.split(":");

        this.indiceQRCode = StringUtil.toInteger(strings[0]);
        this.quantidadeTotalQRCodes = StringUtil.toInteger(strings[1]);
    }
}
