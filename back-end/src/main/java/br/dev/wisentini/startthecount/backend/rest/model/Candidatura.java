package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Candidatura")
@Table(
    name = "candidatura",
    schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id_candidato", "id_cargo_eleicao"})}
)
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Candidatura implements Serializable {

    @Id
    @Column(name = "id_candidatura", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_candidato", nullable = false)
    private Candidato candidato;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_cargo_eleicao", nullable = false)
    private CargoEleicao cargoEleicao;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_partido", nullable = false)
    private Partido partido;

    @OneToMany(mappedBy = "candidatura", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<ApuracaoVotosCandidaturaBoletimUrna> apuracoesVotosBoletinsUrna;

    public Candidatura() {
        this.apuracoesVotosBoletinsUrna = new LinkedHashSet<>();
    }

    public Candidatura(Candidato candidato, CargoEleicao cargoEleicao, Partido partido) {
        this();
        this.candidato = candidato;
        this.cargoEleicao = cargoEleicao;
        this.partido = partido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.candidato.hashCode(),
            this.cargoEleicao.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Candidatura candidatura = (Candidatura) object;

        return
            Objects.equals(this.candidato.hashCode(), candidatura.candidato.hashCode()) &&
            Objects.equals(this.cargoEleicao.hashCode(), candidatura.cargoEleicao.hashCode());
    }
}
