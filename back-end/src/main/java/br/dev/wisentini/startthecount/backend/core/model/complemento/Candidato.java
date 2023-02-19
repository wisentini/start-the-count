package br.dev.wisentini.startthecount.backend.core.model.complemento;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Candidato {

    @SerializedName(value = "codigo", alternate = {"codigoTSE"})
    private String codigoTSE;

    private String nome;

    @Override
    public int hashCode() {
        return Objects.hash(this.codigoTSE);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Candidato candidato = (Candidato) object;

        return Objects.equals(this.codigoTSE, candidato.codigoTSE);
    }
}
