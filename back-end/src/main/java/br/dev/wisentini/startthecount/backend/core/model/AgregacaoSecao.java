package br.dev.wisentini.startthecount.backend.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AgregacaoSecao {

    private Secao secaoPrincipal;

    private Secao secaoAgregada;

    private ProcessoEleitoral processoEleitoral;

    @Override
    public int hashCode() {
        return Objects.hash(
            this.secaoPrincipal.hashCode(),
            this.secaoAgregada.hashCode(),
            this.processoEleitoral.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        AgregacaoSecao agregacaoSecao = (AgregacaoSecao) object;

        return
            Objects.equals(this.secaoPrincipal.hashCode(), agregacaoSecao.secaoPrincipal.hashCode()) &&
            Objects.equals(this.secaoAgregada.hashCode(), agregacaoSecao.secaoAgregada.hashCode()) &&
            Objects.equals(this.processoEleitoral.hashCode(), agregacaoSecao.processoEleitoral.hashCode());
    }
}
