package br.dev.wisentini.startthecount.backend.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder(value = {"status", "content", "timestamp"})
public class ApiResponse<T> {

    protected HttpStatus status;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    protected T content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime timestamp;

    public ApiResponse(T content, HttpStatus status) {
        this.content = content;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
