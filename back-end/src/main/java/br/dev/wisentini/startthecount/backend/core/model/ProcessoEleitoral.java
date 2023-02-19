package br.dev.wisentini.startthecount.backend.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
public class ProcessoEleitoral {

    private Integer codigoTSE;

    private String nome;

    private OrigemConfiguracaoProcessoEleitoral origemConfiguracao;

    @Override
    public int hashCode() {
        return Objects.hash(this.codigoTSE);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        ProcessoEleitoral processoEleitoral = (ProcessoEleitoral) object;

        return Objects.equals(this.codigoTSE, processoEleitoral.codigoTSE);
    }
}
