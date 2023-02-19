package br.dev.wisentini.startthecount.backend.rest.exception;

public class CampoFaltanteException extends RuntimeException {

    public CampoFaltanteException(String message) {
        super(message);
    }
}
