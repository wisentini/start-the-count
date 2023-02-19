package br.dev.wisentini.startthecount.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

import java.time.LocalDate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Pleito")
@Table(name = "pleito", schema = "public")
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class Pleito implements Serializable {

    @Id
    @Column(name = "id_pleito", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cod_tse_pleito", unique = true, nullable = false)
    private Integer codigoTSE;

    @Column(name = "nome", length = 63)
    private String nome;

    @Column(name = "turno", nullable = false)
    private Integer turno;

    @Column(name = "data", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_processo_eleitoral", nullable = false)
    private ProcessoEleitoral processoEleitoral;

    @OneToMany(mappedBy = "pleito", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<SecaoPleito> secoes;

    @OneToMany(mappedBy = "pleito", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Eleicao> eleicoes;

    public Pleito() {
        this.secoes = new LinkedHashSet<>();
        this.eleicoes = new LinkedHashSet<>();
    }

    public Pleito(Integer codigoTSE, String nome, Integer turno, LocalDate data, ProcessoEleitoral processoEleitoral) {
        this();
        this.codigoTSE = codigoTSE;
        this.nome = nome;
        this.turno = turno;
        this.data = data;
        this.processoEleitoral = processoEleitoral;
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

        Pleito pleito = (Pleito) object;

        return Objects.equals(this.codigoTSE, pleito.codigoTSE);
    }

    @JsonIgnore
    public Integer getAno() {
        return this.data.getYear();
    }
}
