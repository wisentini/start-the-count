package br.dev.wisentini.startthecount.backend.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TipoCargo {

    private Integer codigoTSE;

    private String nome;

    public TipoCargo(Integer codigoTSE) {
        this.codigoTSE = codigoTSE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codigoTSE);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        TipoCargo tipoCargo = (TipoCargo) object;

        return Objects.equals(this.codigoTSE, tipoCargo.codigoTSE);
    }
}
