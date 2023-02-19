package br.dev.wisentini.startthecount.backend.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
public class Pleito {

    private Integer codigoTSE;

    private String nome;

    private Turno turno;

    private LocalDate data;

    private ProcessoEleitoral processoEleitoral;

    @Override
    public int hashCode() {
        return Objects.hash(this.codigoTSE);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Pleito pleito = (Pleito) object;

        return Objects.equals(this.codigoTSE, pleito.codigoTSE);
    }

    public Integer getAno() {
        return this.data.getYear();
    }
}
