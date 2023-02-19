package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Zona")
@Table(
    name = "zona",
    schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"num_tse_zona", "id_uf"})}
)
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Zona implements Serializable {

    @Id
    @Column(name = "id_zona", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "num_tse_zona", unique = true, nullable = false)
    private Integer numeroTSE;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_uf", nullable = false)
    private UF uf;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Secao> secoes;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<LocalVotacao> locaisVotacao;

    public Zona() {
        this.secoes = new LinkedHashSet<>();
        this.locaisVotacao = new LinkedHashSet<>();
    }

    public Zona(Integer numeroTSE, UF uf) {
        this();
        this.numeroTSE = numeroTSE;
        this.uf = uf;
    }

    public UF getUF() {
        return this.uf;
    }

    public void setUF(UF uf) {
        this.uf = uf;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numeroTSE, this.uf.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Zona zona = (Zona) object;

        return
            Objects.equals(this.numeroTSE, zona.numeroTSE) &&
            Objects.equals(this.uf.hashCode(), zona.uf.hashCode());
    }
}
