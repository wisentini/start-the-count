package br.dev.wisentini.startthecount.backend.core.model.complemento;

import br.dev.wisentini.startthecount.backend.core.model.CargoId;
import br.dev.wisentini.startthecount.backend.core.model.PartidoId;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
public class Eleicao {

    @SerializedName(value = "codigo", alternate = {"codigoTSE"})
    private Integer codigoTSE;

    private String nome;

    private List<PartidoCargo> partidosPorCargos;

    @Override
    public int hashCode() {
        return Objects.hash(this.codigoTSE);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        Eleicao eleicao = (Eleicao) object;

        return Objects.equals(this.codigoTSE, eleicao.codigoTSE);
    }

    public Map<CargoId, Cargo> getCargos() {
        Map<CargoId, Cargo> cargos = new HashMap<>();

        for (PartidoCargo partidoCargo : this.partidosPorCargos) {
            Cargo cargo = partidoCargo.getCargo();

            cargos.put(
                new CargoId(cargo.getCodigoTSE()),
                cargo
            );
        }

        return cargos;
    }

    public Map<PartidoId, Partido> getPartidos() {
        Map<PartidoId, Partido> partidos = new HashMap<>();

        for (PartidoCargo partidoCargo : this.partidosPorCargos) {
            for (CandidaturaPartido candidaturaPartido : partidoCargo.getCandidaturasPorPartidos()) {
                Partido partido = candidaturaPartido.getPartido();

                partidos.put(
                    new PartidoId(partido.getNumeroTSE()),
                    partido
                );
            }
        }

        return partidos;
    }

    public Map<CandidatoId, Candidato> getCandidatosTitulares() {
        Map<CandidatoId, Candidato> candidatosTitulares = new HashMap<>();

        for (PartidoCargo partidoCargo : this.partidosPorCargos) {
            candidatosTitulares.putAll(partidoCargo.getCandidatosTitulares());
        }

        return candidatosTitulares;
    }
}
