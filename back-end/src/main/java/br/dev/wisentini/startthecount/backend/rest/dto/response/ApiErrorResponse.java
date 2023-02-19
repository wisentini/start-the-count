package br.dev.wisentini.startthecount.backend.rest.dto.response;

import br.dev.wisentini.startthecount.backend.rest.exception.ApiError;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
public class ApiErrorResponse extends ApiResponse<Set<ApiError>> {

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String description;

    public ApiErrorResponse(Set<ApiError> errors, HttpStatus status) {
        super(errors, status);
    }

    public ApiErrorResponse(Set<ApiError> errors, String description, HttpStatus status) {
        this(errors, status);
        this.description = description;
    }

    public ApiErrorResponse(ApiError error, HttpStatus status) {
        this(Set.of(error), status);
    }
}
