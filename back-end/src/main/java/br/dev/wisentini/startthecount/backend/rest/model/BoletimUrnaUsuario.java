package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "BoletimUrnaUsuario")
@Table(
    name = "boletim_urna_usuario",
    schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id_boletim_urna", "id_usuario"})}
)
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class BoletimUrnaUsuario implements Serializable {

    @Id
    @Column(name = "id_boletim_urna_usuario", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_boletim_urna", nullable = false)
    private BoletimUrna boletimUrna;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "coletado_em", insertable = false, updatable = false, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime coletadoEm;

    @Column(name = "atualizado_em", insertable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime atualizadoEm;

    public BoletimUrnaUsuario(BoletimUrna boletimUrna, Usuario usuario) {
        this.boletimUrna = boletimUrna;
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.boletimUrna.hashCode(),
            this.usuario.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        BoletimUrnaUsuario boletimUrnaUsuario = (BoletimUrnaUsuario) object;

        return
            Objects.equals(this.boletimUrna.hashCode(), boletimUrnaUsuario.boletimUrna.hashCode()) &&
            Objects.equals(this.usuario.hashCode(), boletimUrnaUsuario.usuario.hashCode());
    }
}
