package br.dev.wisentini.startthecount.backend.rest.exception;

public class CampoInvalidoException extends RuntimeException {

    public CampoInvalidoException(String message) {
        super(message);
    }
}
