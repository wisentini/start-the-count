package br.dev.wisentini.startthecount.backend.rest.dto.creation;

import br.dev.wisentini.startthecount.backend.rest.exception.CampoFaltanteException;
import br.dev.wisentini.startthecount.backend.rest.exception.CampoInvalidoException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCreationDTO {

    @NotBlank(message = "O username deve ser informado para criar um usuário.")
    @Size(min = 1, max = 31, message = "O username deve possuir no mínimo 1 caractere e no máximo 31 caracteres.")
    private String username;

    @NotBlank(message = "A senha deve ser informada para criar um usuário.")
    @Size(min = 8, max = 72, message = "A senha deve possuir no mínimo 8 caracteres e no máximo 72 caracteres.")
    private String senha;

    @Size(min = 1, max = 127, message = "O nome deve possuir no mínimo 1 caractere e no máximo 127 caracteres.")
    private String nome;

    @Size(min = 1, max = 127, message = "O sobrenome deve possuir no mínimo 1 caractere e no máximo 127 caracteres.")
    private String sobrenome;

    @NotNull(message = "O(s) nome(s) do(s) papel(papéis) de usuário deve(m) ser informado(s) para criar um usuário.")
    @NotEmpty(message = "O(s) nome(s) do(s) papel(papéis) de usuário deve(m) ser informado(s) para criar um usuário.")
    private Set<String> nomesPapeis;

    public void validate() {
        if (StringUtils.isBlank(this.username)) {
            throw new CampoFaltanteException("O username deve ser informado para criar um usuário.");
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

        if (!Objects.isNull(this.nome) && (this.nome.length() < 1 || this.nome.length() > 127)) {
            throw new CampoInvalidoException("O nome deve possuir no mínimo 1 caractere e no máximo 127 caracteres.");
        }

        if (!Objects.isNull(this.sobrenome) && (this.sobrenome.length() < 1 || this.sobrenome.length() > 127)) {
            throw new CampoInvalidoException("O sobrenome deve possuir no mínimo 1 caractere e no máximo 127 caracteres.");
        }

        if (Objects.isNull(this.nomesPapeis) || this.nomesPapeis.isEmpty()) {
            throw new CampoFaltanteException("O(s) nome(s) do(s) papel(papéis) de usuário deve(m) ser informado(s) para criar um usuário.");
        }
    }
}
