package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Permissao")
@Table(name = "permissao", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Permissao implements Serializable {

    @Id
    @Column(name = "id_permissao", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", unique = true, nullable = false, length = 31)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 63)
    private String descricao;

    @OneToMany(mappedBy = "permissao", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<PapelPermissao> papeis;

    public Permissao() {
        this.papeis = new LinkedHashSet<>();
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

        Permissao permissao = (Permissao) object;

        return Objects.equals(this.nome, permissao.nome);
    }
}
