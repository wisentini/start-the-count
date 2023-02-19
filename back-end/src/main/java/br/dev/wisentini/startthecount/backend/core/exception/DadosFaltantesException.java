package br.dev.wisentini.startthecount.backend.core.exception;

import java.util.Map;

public class DadosFaltantesException extends RuntimeException {

    private Map<String, String> dadosFaltantes;

    public DadosFaltantesException(String message) {
        super(message);
    }

    public DadosFaltantesException(String message, Map<String, String> dadosFaltantes) {
        this(message);
        this.dadosFaltantes = dadosFaltantes;
    }

    public Map<String, String> getDadosFaltantes() {
        return this.dadosFaltantes;
    }
}
