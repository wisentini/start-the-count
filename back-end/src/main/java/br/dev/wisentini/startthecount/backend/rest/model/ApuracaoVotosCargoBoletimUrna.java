package br.dev.wisentini.startthecount.backend.rest.model;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.Objects;

@Entity(name = "ApuracaoVotosCargo")
@Table(
    name = "apuracao_votos_cargo_boletim_urna",
    schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id_cargo_eleicao", "id_boletim_urna"})})
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class ApuracaoVotosCargoBoletimUrna implements Serializable {

    @Id
    @Column(name = "id_apuracao_votos_cargo_boletim_urna", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_cargo_eleicao", nullable = false)
    private CargoEleicao cargoEleicao;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_boletim_urna", nullable = false)
    private BoletimUrna boletimUrna;

    @Column(name = "qtde_eleitores_aptos")
    private Integer quantidadeEleitoresAptos;

    @Column(name = "qtde_comparecimento_cargo_sem_candidatos")
    private Integer quantidadeComparecimentoCargoSemCandidatos;

    @Column(name = "qtde_votos_nominais", nullable = false)
    private Integer quantidadeVotosNominais;

    @Column(name = "qtde_votos_de_legenda", nullable = false)
    private Integer quantidadeVotosDeLegenda;

    @Column(name = "qtde_votos_em_branco", nullable = false)
    private Integer quantidadeVotosEmBranco;

    @Column(name = "qtde_votos_nulos", nullable = false)
    private Integer quantidadeVotosNulos;

    @Column(name = "total_votos_apurados", nullable = false)
    private Integer totalVotosApurados;

    public ApuracaoVotosCargoBoletimUrna(CargoEleicao cargoEleicao, BoletimUrna boletimUrna, Integer quantidadeEleitoresAptos, Integer quantidadeComparecimentoCargoSemCandidatos, Integer quantidadeVotosNominais, Integer quantidadeVotosDeLegenda, Integer quantidadeVotosEmBranco, Integer quantidadeVotosNulos, Integer totalVotosApurados) {
        this.cargoEleicao = cargoEleicao;
        this.boletimUrna = boletimUrna;
        this.quantidadeEleitoresAptos = quantidadeEleitoresAptos;
        this.quantidadeComparecimentoCargoSemCandidatos = quantidadeComparecimentoCargoSemCandidatos;
        this.quantidadeVotosNominais = quantidadeVotosNominais;
        this.quantidadeVotosDeLegenda = quantidadeVotosDeLegenda;
        this.quantidadeVotosEmBranco = quantidadeVotosEmBranco;
        this.quantidadeVotosNulos = quantidadeVotosNulos;
        this.totalVotosApurados = totalVotosApurados;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.cargoEleicao.hashCode(),
            this.boletimUrna.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (object == null) return false;

        if (this.getClass() != object.getClass()) return false;

        ApuracaoVotosCargoBoletimUrna apuracaoVotosCargoBoletimUrna = (ApuracaoVotosCargoBoletimUrna) object;

        return
            Objects.equals(this.cargoEleicao.hashCode(), apuracaoVotosCargoBoletimUrna.cargoEleicao.hashCode()) &&
            Objects.equals(this.boletimUrna.hashCode(), apuracaoVotosCargoBoletimUrna.boletimUrna.hashCode());
    }
}
