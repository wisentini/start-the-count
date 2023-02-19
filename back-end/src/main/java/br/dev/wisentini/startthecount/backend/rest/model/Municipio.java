package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Municipio")
@Table(name = "municipio", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome", "id_uf"})})
@Getter
@Setter
@ToString(doNotUseGetters = true)

public class Municipio implements Serializable {

    @Id
    @Column(name = "id_municipio", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cod_tse_municipio", unique = true, nullable = false)
    private Integer codigoTSE;

    @Column(name = "cod_ibge_municipio", unique = true)
    private Integer codigoIBGE;

    @Column(name = "nome", nullable = false, length = 63)
    private String nome;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_uf", nullable = false)
    private UF uf;

    @OneToMany(mappedBy = "municipio", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<LocalVotacao> locaisVotacao;

    public Municipio() {
        this.locaisVotacao = new LinkedHashSet<>();
    }

    public Municipio(String nome, UF uf) {
        this();
        this.nome = nome;
        this.uf = uf;
    }

    public Municipio(Integer codigoTSE, String nome, UF uf) {
        this(nome, uf);
        this.codigoTSE = codigoTSE;
    }

    public Municipio(Integer codigoTSE, Integer codigoIBGE, String nome, UF uf) {
        this(codigoTSE, nome, uf);
        this.codigoIBGE = codigoIBGE;
    }

    public UF getUF() {
        return this.uf;
    }

    public void setUF(UF uf) {
        this.uf = uf;
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

        Municipio municipio = (Municipio) object;

        return Objects.equals(this.codigoTSE, municipio.codigoTSE);
    }
}
