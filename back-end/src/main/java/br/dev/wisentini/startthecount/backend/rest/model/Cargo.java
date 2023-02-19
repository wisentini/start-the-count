package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Cargo")
@Table(name = "cargo", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Cargo implements Serializable {

    @Id
    @Column(name = "id_cargo", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cod_tse_cargo", unique = true, nullable = false)
    private Integer codigoTSE;

    @Column(name = "nome", length = 31)
    private String nome;

    @Column(name = "nome_abreviado", length = 15)
    private String nomeAbreviado;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_tipo_cargo", nullable = false)
    private TipoCargo tipo;

    @OneToMany(mappedBy = "cargo", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<CargoEleicao> eleicoes;

    public Cargo() {
        this.eleicoes = new LinkedHashSet<>();
    }

    public Cargo(Integer codigoTSE, String nome, String nomeAbreviado, TipoCargo tipo) {
        this();
        this.codigoTSE = codigoTSE;
        this.nome = nome;
        this.nomeAbreviado = nomeAbreviado;
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codigoTSE);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Cargo cargo = (Cargo) object;

        return Objects.equals(this.codigoTSE, cargo.codigoTSE);
    }
}
