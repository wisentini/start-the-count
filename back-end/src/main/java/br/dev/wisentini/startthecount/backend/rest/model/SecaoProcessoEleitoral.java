package br.dev.wisentini.startthecount.backend.rest.model;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.Objects;

@Entity(name = "SecaoProcessoEleitoral")
@Table(name = "secao_processo_eleitoral", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_secao", "id_processo_eleitoral"})})
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class SecaoProcessoEleitoral implements Serializable {

    @Id
    @Column(name = "id_secao_processo_eleitoral", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_secao", nullable = false)
    private Secao secao;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_processo_eleitoral", nullable = false)
    private ProcessoEleitoral processoEleitoral;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_local_votacao", nullable = false)
    private LocalVotacao localVotacao;

    public SecaoProcessoEleitoral(Secao secao, ProcessoEleitoral processoEleitoral, LocalVotacao localVotacao) {
        this.secao = secao;
        this.processoEleitoral = processoEleitoral;
        this.localVotacao = localVotacao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.secao.hashCode(),
            this.processoEleitoral.hashCode()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (object == null) return false;

        if (this.getClass() != object.getClass()) return false;

        SecaoProcessoEleitoral secaoProcessoEleitoral = (SecaoProcessoEleitoral) object;

        return
            Objects.equals(this.secao.hashCode(), secaoProcessoEleitoral.secao.hashCode()) &&
            Objects.equals(this.processoEleitoral.hashCode(), secaoProcessoEleitoral.processoEleitoral.hashCode());
    }
}
