package br.dev.wisentini.startthecount.backend.rest.model;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.Objects;

@Entity(name = "ApuracaoVotosCandidatura")
@Table(
    name = "apuracao_votos_candidatura_boletim_urna",
    schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id_candidatura", "id_boletim_urna"})}
)
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class ApuracaoVotosCandidaturaBoletimUrna implements Serializable {

    @Id
    @Column(name = "id_apuracao_votos_candidatura_boletim_urna", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_candidatura", nullable = false)
    private Candidatura candidatura;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_boletim_urna", nullable = false)
    private BoletimUrna boletimUrna;

    @Column(name = "total_votos_apurados", nullable = false)
    private Integer totalVotosApurados;

    public ApuracaoVotosCandidaturaBoletimUrna(Candidatura candidatura, BoletimUrna boletimUrna, Integer totalVotosApurados) {
        this.candidatura = candidatura;
        this.boletimUrna = boletimUrna;
        this.totalVotosApurados = totalVotosApurados;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.candidatura.hashCode(),
            this.boletimUrna.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (object == null) return false;

        if (this.getClass() != object.getClass()) return false;

        ApuracaoVotosCandidaturaBoletimUrna apuracaoVotosCandidaturaBoletimUrna = (ApuracaoVotosCandidaturaBoletimUrna) object;

        return
            Objects.equals(this.candidatura.hashCode(), apuracaoVotosCandidaturaBoletimUrna.candidatura.hashCode()) &&
            Objects.equals(this.boletimUrna.hashCode(), apuracaoVotosCandidaturaBoletimUrna.boletimUrna.hashCode());
    }
}
