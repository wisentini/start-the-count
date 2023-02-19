package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "ProcessoEleitoral")
@Table(name = "processo_eleitoral", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class ProcessoEleitoral implements Serializable {

    @Id
    @Column(name = "id_processo_eleitoral", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cod_tse_processo_eleitoral", unique = true, nullable = false)
    private Integer codigoTSE;

    @Column(name = "nome")
    private String nome;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_origem_configuracao_processo_eleitoral")
    private OrigemConfiguracaoProcessoEleitoral origemConfiguracao;

    @OneToMany(mappedBy = "processoEleitoral", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Pleito> pleitos;

    @OneToMany(mappedBy = "processoEleitoral", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<SecaoProcessoEleitoral> secoes;

    @OneToMany(mappedBy = "processoEleitoral", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<AgregacaoSecao> secoesAgregadas;

    public ProcessoEleitoral() {
        this.pleitos = new LinkedHashSet<>();
        this.secoes = new LinkedHashSet<>();
        this.secoesAgregadas = new LinkedHashSet<>();
    }

    public ProcessoEleitoral(Integer codigoTSE, String nome) {
        this();
        this.codigoTSE = codigoTSE;
        this.nome = nome;
    }

    public ProcessoEleitoral(Integer codigoTSE, String nome, OrigemConfiguracaoProcessoEleitoral origemConfiguracao) {
        this(codigoTSE, nome);
        this.origemConfiguracao = origemConfiguracao;
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

        ProcessoEleitoral processoEleitoral = (ProcessoEleitoral) object;

        return Objects.equals(this.codigoTSE, processoEleitoral.codigoTSE);
    }
}
