package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "CargoEleicao")
@Table(
    name = "cargo_eleicao",
    schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id_cargo", "id_eleicao"})})
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class CargoEleicao implements Serializable {

    @Id
    @Column(name = "id_cargo_eleicao", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo cargo;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_eleicao", nullable = false)
    private Eleicao eleicao;

    @OneToMany(mappedBy = "cargoEleicao", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Candidatura> candidaturas;

    @OneToMany(mappedBy = "cargoEleicao", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<ApuracaoVotosCargoBoletimUrna> apuracoesVotosCargosBoletimUrna;

    @OneToMany(mappedBy = "cargoEleicao", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<ApuracaoVotosPartidoBoletimUrna> apuracoesVotosPartidosBoletimUrna;

    public CargoEleicao() {
        this.candidaturas = new LinkedHashSet<>();
        this.apuracoesVotosCargosBoletimUrna = new LinkedHashSet<>();
        this.apuracoesVotosPartidosBoletimUrna = new LinkedHashSet<>();
    }

    public CargoEleicao(Cargo cargo, Eleicao eleicao) {
        this();
        this.cargo = cargo;
        this.eleicao = eleicao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cargo.hashCode(), this.eleicao.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        CargoEleicao cargoEleicao = (CargoEleicao) object;

        return
            Objects.equals(this.cargo.hashCode(), cargoEleicao.cargo.hashCode()) &&
            Objects.equals(this.eleicao.hashCode(), cargoEleicao.eleicao.hashCode());
    }
}
