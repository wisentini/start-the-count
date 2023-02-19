package br.dev.wisentini.startthecount.backend.rest.model;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.Objects;

@Entity(name = "ApuracaoVotosPartido")
@Table(name = "apuracao_votos_partido_boletim_urna", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_partido", "id_boletim_urna", "id_cargo_eleicao"})})
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class ApuracaoVotosPartidoBoletimUrna implements Serializable {

    @Id
    @Column(name = "id_apuracao_votos_partido_boletim_urna", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_partido", nullable = false)
    private Partido partido;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_boletim_urna", nullable = false)
    private BoletimUrna boletimUrna;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_cargo_eleicao", nullable = false)
    private CargoEleicao cargoEleicao;

    @Column(name = "qtde_votos_de_legenda", nullable = false)
    private Integer quantidadeVotosDeLegenda;

    @Column(name = "total_votos_apurados", nullable = false)
    private Integer totalVotosApurados;

    public ApuracaoVotosPartidoBoletimUrna(Partido partido, BoletimUrna boletimUrna, CargoEleicao cargoEleicao, Integer quantidadeVotosDeLegenda, Integer totalVotosApurados) {
        this.partido = partido;
        this.boletimUrna = boletimUrna;
        this.cargoEleicao = cargoEleicao;
        this.quantidadeVotosDeLegenda = quantidadeVotosDeLegenda;
        this.totalVotosApurados = totalVotosApurados;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.partido.hashCode(),
            this.cargoEleicao.hashCode(),
            this.boletimUrna.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (object == null) return false;

        if (this.getClass() != object.getClass()) return false;

        ApuracaoVotosPartidoBoletimUrna apuracaoVotosPartidoBoletimUrna = (ApuracaoVotosPartidoBoletimUrna) object;

        return
            Objects.equals(this.partido.hashCode(), apuracaoVotosPartidoBoletimUrna.partido.hashCode()) &&
            Objects.equals(this.cargoEleicao.hashCode(), apuracaoVotosPartidoBoletimUrna.cargoEleicao.hashCode()) &&
            Objects.equals(this.boletimUrna.hashCode(), apuracaoVotosPartidoBoletimUrna.boletimUrna.hashCode());
    }
}
