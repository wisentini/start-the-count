package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "UF")
@Table(name = "uf", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class UF implements Serializable {

    @Id
    @Column(name = "id_uf", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cod_ibge_uf", unique = true)
    private Integer codigoIBGE;

    @Column(name = "nome", unique = true, nullable = false, length = 31)
    private String nome;

    @Column(name = "sigla", unique = true, nullable = false, length = 2, columnDefinition = "bpchar")
    private String sigla;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;

    @OneToMany(mappedBy = "uf", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Municipio> municipios;

    @OneToMany(mappedBy = "uf", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Zona> zonas;

    public UF() {
        this.municipios = new LinkedHashSet<>();
        this.zonas = new LinkedHashSet<>();
    }

    public UF(Integer codigoIBGE, String nome, String sigla, Regiao regiao) {
        this();
        this.codigoIBGE = codigoIBGE;
        this.nome = nome;
        this.sigla = sigla;
        this.regiao = regiao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.sigla);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        UF uf = (UF) object;

        return Objects.equals(this.sigla, uf.sigla);
    }
}
