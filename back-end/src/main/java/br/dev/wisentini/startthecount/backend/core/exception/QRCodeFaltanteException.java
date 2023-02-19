package br.dev.wisentini.startthecount.backend.core.exception;

public class QRCodeFaltanteException extends RuntimeException {

    public QRCodeFaltanteException(String message) {
        super(message);
    }
}
