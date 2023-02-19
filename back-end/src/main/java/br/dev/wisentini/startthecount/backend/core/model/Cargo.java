package br.dev.wisentini.startthecount.backend.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cargo {

    private Integer codigoTSE;

    private String nome;

    private String nomeAbreviado;

    private TipoCargo tipo;

    private String versaoPacoteDados;

    public Cargo (Integer codigoTSE) {
        this.codigoTSE = codigoTSE;
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

        Cargo cargo = (Cargo) object;

        return Objects.equals(this.codigoTSE, cargo.codigoTSE);
    }
}
