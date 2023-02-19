package br.dev.wisentini.startthecount.backend.core.model.complemento;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Cargo {

    @SerializedName(value = "codigo", alternate = {"codigoTSE"})
    private Integer codigoTSE;

    private String nomeMasculino;

    private String nomeFeminino;

    private String nomeNeutro;

    private String nomeAbreviado;

    private String versao;

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
