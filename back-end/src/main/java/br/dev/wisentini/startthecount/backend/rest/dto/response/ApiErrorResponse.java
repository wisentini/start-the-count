package br.dev.wisentini.startthecount.backend.rest.dto.response;

import br.dev.wisentini.startthecount.backend.rest.exception.ApiError;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
@JsonIgnoreProperties(value = {"content"})
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

    @Override
    @JsonProperty(value = "errors")
    public Set<ApiError> getContent() {
        return this.content;
    }
}
