package br.dev.wisentini.startthecount.backend.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
public class LocalVotacao {

    private Integer numeroTSE;

    private String nome;

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

        LocalVotacao localVotacao = (LocalVotacao) object;

        return
            Objects.equals(this.numeroTSE, localVotacao.numeroTSE) &&
            Objects.equals(this.zona.hashCode(), localVotacao.zona.hashCode());
    }
}
