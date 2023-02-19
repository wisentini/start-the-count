package br.dev.wisentini.startthecount.backend.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class SecaoProcessoEleitoral {

    private Secao secao;

    private ProcessoEleitoral processoEleitoral;

    private LocalVotacao localVotacao;

    @Override
    public int hashCode() {
        return Objects.hash(this.secao.hashCode(), this.processoEleitoral.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        SecaoProcessoEleitoral secaoProcessoEleitoral = (SecaoProcessoEleitoral) object;

        return
            Objects.equals(this.secao.hashCode(), secaoProcessoEleitoral.secao.hashCode()) &&
            Objects.equals(this.processoEleitoral.hashCode(), secaoProcessoEleitoral.processoEleitoral.hashCode());
    }
}
