package br.dev.wisentini.startthecount.backend.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class ApuracaoVotosCargo {

    private Cargo cargo;

    private Eleicao eleicao;

    private Integer quantidadeEleitoresAptos;

    private Integer quantidadeComparecimentoCargoSemCandidatos;

    private Integer quantidadeVotosNominais;

    private Integer quantidadeVotosDeLegenda;

    private Integer quantidadeVotosEmBranco;

    private Integer quantidadeVotosNulos;

    private Integer totalVotosApurados;

    public ApuracaoVotosCargo() {
        this.quantidadeEleitoresAptos = 0;
        this.quantidadeVotosNominais = 0;
        this.quantidadeVotosEmBranco = 0;
        this.quantidadeVotosNulos = 0;
        this.totalVotosApurados = 0;
    }

    public ApuracaoVotosCargo(Cargo cargo, Eleicao eleicao) {
        this();
        this.cargo = cargo;
        this.eleicao = eleicao;
    }

    public ApuracaoVotosCargo(Cargo cargo, Eleicao eleicao, Integer quantidadeEleitoresAptos) {
        this(cargo, eleicao);
        this.quantidadeEleitoresAptos = quantidadeEleitoresAptos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cargo.hashCode(), this.eleicao.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        ApuracaoVotosCargo apuracaoVotosCargo = (ApuracaoVotosCargo) object;

        return
            Objects.equals(this.cargo.hashCode(), apuracaoVotosCargo.cargo.hashCode()) &&
            Objects.equals(this.eleicao.hashCode(), apuracaoVotosCargo.eleicao.hashCode());
    }
}
