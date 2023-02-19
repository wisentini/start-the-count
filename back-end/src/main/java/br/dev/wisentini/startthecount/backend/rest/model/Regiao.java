package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Regiao")
@Table(name = "regiao", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Regiao implements Serializable {

    @Id
    @Column(name = "id_regiao", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cod_ibge_regiao", unique = true)
    private Integer codigoIBGE;

    @Column(name = "nome", unique = true, nullable = false, length = 15)
    private String nome;

    @Column(name = "sigla", unique = true, nullable = false, length = 2)
    private String sigla;

    @OneToMany(mappedBy = "regiao", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<UF> ufs;

    public Regiao() {
        this.ufs = new LinkedHashSet<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nome);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Regiao regiao = (Regiao) object;

        return Objects.equals(this.nome, regiao.nome);
    }
}
