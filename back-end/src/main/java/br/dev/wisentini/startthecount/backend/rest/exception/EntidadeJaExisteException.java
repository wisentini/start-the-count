package br.dev.wisentini.startthecount.backend.rest.exception;

public class EntidadeJaExisteException extends RuntimeException {

    public EntidadeJaExisteException(String message) {
        super(message);
    }
}
