package br.dev.wisentini.startthecount.backend.rest.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import java.util.Objects;

@Entity(name = "PapelUsuario")
@Table(
    name = "papel_usuario",
    schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id_papel", "id_usuario"})}
)
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class PapelUsuario implements Serializable {

    @Id
    @Column(name = "id_papel_usuario", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_papel", nullable = false)
    private Papel papel;

    public PapelUsuario(Usuario usuario, Papel papel) {
        this.usuario = usuario;
        this.papel = papel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.usuario.hashCode(),
            this.papel.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (object == null) return false;

        if (this.getClass() != object.getClass()) return false;

        PapelUsuario papelUsuario = (PapelUsuario) object;

        return
            Objects.equals(this.usuario.hashCode(), papelUsuario.usuario.hashCode()) &&
            Objects.equals(this.papel.hashCode(), papelUsuario.papel.hashCode());
    }
}
