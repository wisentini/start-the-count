package br.dev.wisentini.startthecount.backend.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class ApuracaoVotosPartido {

    private Partido partido;

    private Cargo cargo;

    private Eleicao eleicao;

    private Integer quantidadeVotosDeLegenda;

    private Integer totalVotosApurados;

    public ApuracaoVotosPartido() {
        this.quantidadeVotosDeLegenda = 0;
        this.totalVotosApurados = 0;
    }

    public ApuracaoVotosPartido(Partido partido, Cargo cargo, Eleicao eleicao) {
        this();
        this.partido = partido;
        this.cargo = cargo;
        this.eleicao = eleicao;
    }

    public ApuracaoVotosPartido(Partido partido, Cargo cargo, Eleicao eleicao, Integer quantidadeVotosDeLegenda) {
        this(partido, cargo, eleicao);
        this.quantidadeVotosDeLegenda = quantidadeVotosDeLegenda;
    }

    public ApuracaoVotosPartido(Partido partido, Cargo cargo, Eleicao eleicao, Integer quantidadeVotosDeLegenda, Integer totalVotosApurados) {
        this(partido, cargo, eleicao, quantidadeVotosDeLegenda);
        this.totalVotosApurados = totalVotosApurados;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.partido.hashCode(), this.cargo.hashCode(), this.eleicao.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        ApuracaoVotosPartido apuracaoVotosPartido = (ApuracaoVotosPartido) object;

        return
            Objects.equals(this.partido.hashCode(), apuracaoVotosPartido.partido.hashCode()) &&
            Objects.equals(this.cargo.hashCode(), apuracaoVotosPartido.cargo.hashCode()) &&
            Objects.equals(this.eleicao.hashCode(), apuracaoVotosPartido.eleicao.hashCode());
    }
}
