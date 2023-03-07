package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "SecaoPleito")
@Table(
    name = "secao_pleito",
    schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id_secao", "id_pleito"})})
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class SecaoPleito implements Serializable {

    @Id
    @Column(name = "id_secao_pleito", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_secao", nullable = false)
    private Secao secao;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_pleito", nullable = false)
    private Pleito pleito;

    @OneToMany(mappedBy = "secaoPleito", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<BoletimUrna> boletinsUrna;

    public SecaoPleito() {
        this.boletinsUrna = new LinkedHashSet<>();
    }

    public SecaoPleito(Secao secao, Pleito pleito) {
        this();
        this.secao = secao;
        this.pleito = pleito;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.secao.hashCode(),
            this.pleito.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (object == null) return false;

        if (this.getClass() != object.getClass()) return false;

        SecaoPleito secaoPleito = (SecaoPleito) object;

        return
            Objects.equals(this.secao.hashCode(), secaoPleito.secao.hashCode()) &&
            Objects.equals(this.pleito.hashCode(), secaoPleito.pleito.hashCode());
    }
}
