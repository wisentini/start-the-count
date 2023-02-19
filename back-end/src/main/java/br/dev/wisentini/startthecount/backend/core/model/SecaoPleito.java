package br.dev.wisentini.startthecount.backend.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SecaoPleito {

    private Secao secao;

    private Pleito pleito;

    private Integer quantidadeEleitoresAptos;

    private Integer quantidadeEleitoresComparecentes;

    private Integer quantidadeEleitoresFaltosos;

    private Integer quantidadeEleitoresHabilitadosPorAnoNascimento;

    @Override
    public int hashCode() {
        return Objects.hash(this.secao.hashCode(), this.pleito.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        SecaoPleito secaoPleito = (SecaoPleito) object;

        return
            Objects.equals(this.secao.hashCode(), secaoPleito.secao.hashCode()) &&
            Objects.equals(this.pleito.hashCode(), secaoPleito.pleito.hashCode());
    }
}
