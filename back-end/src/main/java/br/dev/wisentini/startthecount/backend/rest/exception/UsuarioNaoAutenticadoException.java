package br.dev.wisentini.startthecount.backend.rest.exception;

public class UsuarioNaoAutenticadoException extends RuntimeException {

    public UsuarioNaoAutenticadoException(String message) {
        super(message);
    }
}
