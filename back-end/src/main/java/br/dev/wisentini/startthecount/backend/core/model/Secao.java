package br.dev.wisentini.startthecount.backend.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Secao {

    private Integer numeroTSE;

    private Zona zona;

    private Municipio municipio;

    @Override
    public int hashCode() {
        return Objects.hash(this.numeroTSE, this.zona.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Secao secao = (Secao) object;

        return
            Objects.equals(this.numeroTSE, secao.numeroTSE) &&
            Objects.equals(this.zona.hashCode(), secao.zona.hashCode());
    }
}
