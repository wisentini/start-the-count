package br.dev.wisentini.startthecount.backend.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Candidatura {

    private Integer numeroTSECandidato;

    private Cargo cargo;

    private Eleicao eleicao;

    private Partido partido;

    @Override
    public int hashCode() {
        return Objects.hash(this.numeroTSECandidato, this.cargo.hashCode(), this.eleicao.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Candidatura candidatura = (Candidatura) object;

        return
            Objects.equals(this.numeroTSECandidato, candidatura.numeroTSECandidato) &&
            Objects.equals(this.cargo.hashCode(), candidatura.cargo.hashCode()) &&
            Objects.equals(this.eleicao.hashCode(), candidatura.eleicao.hashCode());
    }
}
