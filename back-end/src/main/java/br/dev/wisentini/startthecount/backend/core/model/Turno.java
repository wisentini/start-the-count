package br.dev.wisentini.startthecount.backend.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Turno {

    PRIMEIRO (1, "1º turno"),
    SEGUNDO  (2, "2º turno");

    private final Integer numero;

    private final String nome;

    public static Optional<Turno> fromNumero(Integer numero) {
        for (Turno value : values()) {
            if (value.numero.equals(numero)) {
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
