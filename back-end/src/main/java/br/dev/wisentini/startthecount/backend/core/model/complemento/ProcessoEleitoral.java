package br.dev.wisentini.startthecount.backend.core.model.complemento;

import br.dev.wisentini.startthecount.backend.core.model.CargoId;
import br.dev.wisentini.startthecount.backend.core.model.EleicaoId;
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
public class ProcessoEleitoral {

    @SerializedName(value = "codigo", alternate = {"codigoTSE"})
    private Integer codigoTSE;

    private List<Eleicao> eleicoes;

    private List<String> consultasPopulares;

    private Municipio municipio;

    private String nome;

    private Pleito pleito;

    @Override
    public int hashCode() {
        return Objects.hash(this.codigoTSE);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        ProcessoEleitoral processoEleitoral = (ProcessoEleitoral) object;

        return Objects.equals(this.codigoTSE, processoEleitoral.codigoTSE);
    }

    public Map<EleicaoId, Eleicao> getEleicoes() {
        Map<EleicaoId, Eleicao> eleicoes = new HashMap<>();

        for (Eleicao eleicao : this.eleicoes) {
            eleicoes.put(
                new EleicaoId(eleicao.getCodigoTSE()),
                eleicao
            );
        }

        return eleicoes;
    }

    public Map<CargoId, Cargo> getCargos() {
        Map<CargoId, Cargo> cargos = new HashMap<>();

        for (Eleicao eleicao : this.eleicoes) {
            cargos.putAll(eleicao.getCargos());
        }

        return cargos;
    }

    public Map<PartidoId, Partido> getPartidos() {
        Map<PartidoId, Partido> partidos = new HashMap<>();

        for (Eleicao eleicao : this.eleicoes) {
            partidos.putAll(eleicao.getPartidos());
        }

        return partidos;
    }

    public Map<CandidatoId, Candidato> getCandidatosTitulares() {
        Map<CandidatoId, Candidato> candidatosTitulares = new HashMap<>();

        for (Eleicao eleicao : this.eleicoes) {
            candidatosTitulares.putAll(eleicao.getCandidatosTitulares());
        }

        return candidatosTitulares;
    }
}
