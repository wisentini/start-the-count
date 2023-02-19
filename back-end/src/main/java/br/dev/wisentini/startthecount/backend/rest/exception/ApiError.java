package br.dev.wisentini.startthecount.backend.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {

    protected final String message;
}
