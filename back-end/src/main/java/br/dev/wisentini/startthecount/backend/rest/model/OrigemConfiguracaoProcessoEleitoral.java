package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "OrigemConfiguracaoProcessoEleitoral")
@Table(name = "origem_configuracao_processo_eleitoral", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class OrigemConfiguracaoProcessoEleitoral implements Serializable {

    @Id
    @Column(name = "id_origem_configuracao_processo_eleitoral", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @Column(name = "nome_abreviado", unique = true)
    private String nomeAbreviado;

    @OneToMany(mappedBy = "origemConfiguracao", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<ProcessoEleitoral> processosEleitorais;

    public OrigemConfiguracaoProcessoEleitoral() {
        this.processosEleitorais = new LinkedHashSet<>();
    }

    public OrigemConfiguracaoProcessoEleitoral(String nome, String nomeAbreviado) {
        this();
        this.nome = nome;
        this.nomeAbreviado = nomeAbreviado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nomeAbreviado);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        OrigemConfiguracaoProcessoEleitoral origemConfiguracaoProcessoEleitoral = (OrigemConfiguracaoProcessoEleitoral) object;

        return Objects.equals(this.nomeAbreviado, origemConfiguracaoProcessoEleitoral.nomeAbreviado);
    }
}
