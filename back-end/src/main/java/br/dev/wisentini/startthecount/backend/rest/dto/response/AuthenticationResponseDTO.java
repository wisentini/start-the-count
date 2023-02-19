package br.dev.wisentini.startthecount.backend.rest.dto.response;

import lombok.*;

import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class AuthenticationResponseDTO extends ApiResponse<String> {

    public AuthenticationResponseDTO(String token) {
        super(token, HttpStatus.OK);
    }
}
