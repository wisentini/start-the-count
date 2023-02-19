package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Fase")
@Table(name = "fase", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Fase implements Serializable {

    @Id
    @Column(name = "id_fase", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cod_tse_fase", unique = true)
    private Integer codigoTSE;

    @Column(name = "nome", unique = true, length = 15)
    private String nome;

    @OneToMany(mappedBy = "fase", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<BoletimUrna> boletinsUrna;

    public Fase() {
        this.boletinsUrna = new LinkedHashSet<>();
    }

    public Fase(Integer codigoTSE, String nome) {
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

        Fase origemBoletimUrna = (Fase) object;

        return Objects.equals(this.codigoTSE, origemBoletimUrna.codigoTSE);
    }
}
