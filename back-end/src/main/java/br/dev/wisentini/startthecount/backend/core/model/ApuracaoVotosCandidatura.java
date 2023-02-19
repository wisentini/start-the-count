package br.dev.wisentini.startthecount.backend.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class ApuracaoVotosCandidatura {

    private Candidatura candidatura;

    private Integer totalVotosApurados;

    public ApuracaoVotosCandidatura() {
        this.totalVotosApurados = 0;
    }

    public ApuracaoVotosCandidatura(Candidatura candidatura) {
        this();
        this.candidatura = candidatura;
    }

    public ApuracaoVotosCandidatura(Candidatura candidatura, Integer totalVotosApurados) {
        this(candidatura);
        this.totalVotosApurados = totalVotosApurados;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.candidatura.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        ApuracaoVotosCandidatura apuracaoVotosCandidatura = (ApuracaoVotosCandidatura) object;

        return Objects.equals(this.candidatura.hashCode(), apuracaoVotosCandidatura.candidatura.hashCode());
    }
}
