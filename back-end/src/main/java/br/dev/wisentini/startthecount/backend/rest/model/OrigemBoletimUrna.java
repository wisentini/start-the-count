package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "OrigemBoletimUrna")
@Table(name = "origem_boletim_urna", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class OrigemBoletimUrna implements Serializable {

    @Id
    @Column(name = "id_origem_boletim_urna", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", unique = true, length = 31)
    private String nome;

    @Column(name = "nome_abreviado", unique = true, length = 7)
    private String nomeAbreviado;

    @OneToMany(mappedBy = "origem", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<BoletimUrna> boletinsUrna;

    public OrigemBoletimUrna() {
        this.boletinsUrna = new LinkedHashSet<>();
    }

    public OrigemBoletimUrna(String nome, String nomeAbreviado) {
        this();
        this.nome = nome;
        this.nomeAbreviado = nomeAbreviado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nomeAbreviado);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        OrigemBoletimUrna origemBoletimUrna = (OrigemBoletimUrna) object;

        return Objects.equals(this.nomeAbreviado, origemBoletimUrna.nomeAbreviado);
    }
}
