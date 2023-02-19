package br.dev.wisentini.startthecount.backend.core.model.complemento;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Candidatura {

    @SerializedName(value = "numero", alternate = {"numeroTSECandidato"})
    private Integer numeroTSECandidato;

    private Candidato titular;

    private List<Candidato> suplentes;
}
