package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Partido")
@Table(name = "partido", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Partido implements Serializable {

    @Id
    @Column(name = "id_partido", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "num_tse_partido", unique = true, nullable = false)
    private Integer numeroTSE;

    @Column(name = "nome", length = 63, nullable = false)
    private String nome;

    @Column(name = "sigla", length = 15, nullable = false)
    private String sigla;

    @OneToMany(mappedBy = "partido", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Candidatura> candidaturas;

    @OneToMany(mappedBy = "partido", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<ApuracaoVotosPartidoBoletimUrna> apuracoesVotosBoletinsUrna;

    public Partido() {
        this.candidaturas = new LinkedHashSet<>();
        this.apuracoesVotosBoletinsUrna = new LinkedHashSet<>();
    }

    public Partido(Integer numeroTSE, String nome, String sigla) {
        this();
        this.numeroTSE = numeroTSE;
        this.nome = nome;
        this.sigla = sigla;
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
