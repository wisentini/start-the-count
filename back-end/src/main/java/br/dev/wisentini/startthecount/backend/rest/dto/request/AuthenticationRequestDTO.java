package br.dev.wisentini.startthecount.backend.rest.dto.request;

import br.dev.wisentini.startthecount.backend.rest.exception.CampoFaltanteException;
import br.dev.wisentini.startthecount.backend.rest.exception.CampoInvalidoException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationRequestDTO {

    @NotBlank(message = "O username deve ser informado para autenticar um usuário.")
    @Size(min = 1, max = 31, message = "O username deve ter no mínimo 1 caractere e no máximo 31 caracteres.")
    private String username;

    @NotBlank(message = "A senha deve ser informada para autenticar um usuário.")
    @Size(min = 1, max = 72, message = "A senha deve ter no mínimo 1 caractere e no máximo 72 caracteres.")
    private String senha;

    public void validate() {
        if (StringUtils.isBlank(this.username)) {
            throw new CampoFaltanteException("O username deve ser informado para autenticar um usuário.");
        }

        if (this.username.length() < 1 || this.username.length() > 31) {
            throw new CampoInvalidoException("O username deve possuir no mínimo 1 caractere e no máximo 31 caracteres.");
        }

        if (StringUtils.isBlank(this.senha)) {
            throw new CampoFaltanteException("A senha deve ser informada para criar um usuário.");
        }

        if (this.senha.length() < 8 || this.senha.length() > 72) {
            throw new CampoInvalidoException("A senha deve possuir no mínimo 8 caracteres e no máximo 72 caracteres.");
        }
    }
}
