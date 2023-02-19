package br.dev.wisentini.startthecount.backend.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Eleicao {

    private Integer codigoTSE;

    private Pleito pleito;

    private String nome;

    private Integer ano;

    private Integer quantidadeVotosCargosMajoritarios;

    private Integer quantidadeVotosCargosProporcionais;

    public Eleicao(Integer codigoTSE, Pleito pleito, Integer ano) {
        this.codigoTSE = codigoTSE;
        this.pleito = pleito;
        this.ano = ano;
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

        Eleicao eleicao = (Eleicao) object;

        return Objects.equals(this.codigoTSE, eleicao.codigoTSE);
    }
}
