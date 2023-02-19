package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Papel")
@Table(name = "papel", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Papel implements Serializable {

    @Id
    @Column(name = "id_papel", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", unique = true, nullable = false, length = 15)
    private String nome;

    @Column(name = "descricao", unique = true, nullable = false, length = 31)
    private String descricao;

    @OneToMany(mappedBy = "papel", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<PapelUsuario> usuarios;

    @OneToMany(mappedBy = "papel", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<PapelPermissao> permissoes;

    public Papel() {
        this.usuarios = new LinkedHashSet<>();
        this.permissoes = new LinkedHashSet<>();
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

        Papel papel = (Papel) object;

        return Objects.equals(this.nome, papel.nome);
    }
}
