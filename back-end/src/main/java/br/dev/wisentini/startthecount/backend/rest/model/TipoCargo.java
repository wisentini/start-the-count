package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "TipoCargo")
@Table(name = "tipo_cargo", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class TipoCargo implements Serializable {

    @Id
    @Column(name = "id_tipo_cargo", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cod_tse_tipo_cargo", unique = true, nullable = false)
    private Integer codigoTSE;

    @Column(name = "nome", unique = true, length = 15)
    private String nome;

    @OneToMany(mappedBy = "tipo", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Cargo> cargos;

    public TipoCargo() {
        this.cargos = new LinkedHashSet<>();
    }

    public TipoCargo(Integer codigoTSE, String nome) {
        this();
        this.codigoTSE = codigoTSE;
        this.nome = nome;
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

        TipoCargo tipoCargo = (TipoCargo) object;

        return Objects.equals(this.codigoTSE, tipoCargo.codigoTSE);
    }
}
