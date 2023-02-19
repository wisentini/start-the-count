package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Secao")
@Table(name = "secao", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"num_tse_secao", "id_zona"})})
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Secao implements Serializable {

    @Id
    @Column(name = "id_secao", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "num_tse_secao", nullable = false)
    private Integer numeroTSE;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;

    @OneToMany(mappedBy = "secao", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<SecaoProcessoEleitoral> processosEleitorais;

    @OneToMany(mappedBy = "secao", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<SecaoPleito> pleitos;

    @OneToMany(mappedBy = "secaoAgregada", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<AgregacaoSecao> secoesPrincipais;

    @OneToMany(mappedBy = "secaoPrincipal", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<AgregacaoSecao> secoesAgregadas;

    public Secao() {
        this.processosEleitorais = new LinkedHashSet<>();
        this.pleitos = new LinkedHashSet<>();
        this.secoesPrincipais = new LinkedHashSet<>();
        this.secoesAgregadas = new LinkedHashSet<>();
    }

    public Secao(Integer numeroTSE, Zona zona) {
        this();
        this.numeroTSE = numeroTSE;
        this.zona = zona;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.getNumeroTSE(),
            this.zona.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Secao secao = (Secao) object;

        return
            Objects.equals(this.getNumeroTSE(), secao.getNumeroTSE()) &&
            Objects.equals(this.zona.hashCode(), secao.zona.hashCode());
    }
}
