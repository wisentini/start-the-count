package br.dev.wisentini.startthecount.backend.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Fase {

    SIMULADO    (1, "Simulado", "S"),
    OFICIAL     (2, "Oficial", "O"),
    TREINAMENTO (3, "Treinamento", "T");

    private final Integer codigoTSE;

    private final String nome;

    private final String nomeAbreviado;

    public static Optional<Fase> fromNomeAbreviado(String nomeAbreviado) {
        if (Objects.isNull(nomeAbreviado)) {
            throw new NullPointerException(
                "A fase dos dados do boletim de urna deve ser informada."
            );
        }

        for (Fase value : values()) {
            if (value.nomeAbreviado.equalsIgnoreCase(nomeAbreviado)) {
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
