package br.dev.wisentini.startthecount.backend.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Partido {

    private Integer numeroTSE;

    private String nome;

    private String sigla;

    public Partido(Integer numeroTSE) {
        this.numeroTSE = numeroTSE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numeroTSE);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Partido partido = (Partido) object;

        return Objects.equals(this.numeroTSE, partido.numeroTSE);
    }
}
