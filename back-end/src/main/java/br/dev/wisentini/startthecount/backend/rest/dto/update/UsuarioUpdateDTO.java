package br.dev.wisentini.startthecount.backend.rest.dto.update;

import br.dev.wisentini.startthecount.backend.rest.exception.CampoFaltanteException;
import br.dev.wisentini.startthecount.backend.rest.exception.CampoInvalidoException;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDTO {

    @Size(min = 8, max = 72, message = "A senha do usuário deve ter no mínimo 8 caracteres e no máximo 72 caracteres.")
    private String senha;

    @Size(min = 1, max = 127, message = "O nome do usuário deve ter no mínimo 1 caractere e no máximo 127 caracteres.")
    private String nome;

    @Size(min = 1, max = 127, message = "O sobrenome do usuário deve ter no mínimo 1 caractere e no máximo 127 caracteres.")
    private String sobrenome;

    public void validate() {
        if (!StringUtils.isBlank(this.senha) && (this.senha.length() < 8 || this.senha.length() > 72)) {
            throw new CampoInvalidoException("A senha deve possuir no mínimo 8 caracteres e no máximo 72 caracteres.");
        }

        if (!Objects.isNull(this.nome) && (this.nome.length() < 1 || this.nome.length() > 127)) {
            throw new CampoInvalidoException("O nome deve possuir no mínimo 1 caractere e no máximo 127 caracteres.");
        }

        if (!Objects.isNull(this.sobrenome) && (this.sobrenome.length() < 1 || this.sobrenome.length() > 127)) {
            throw new CampoInvalidoException("O sobrenome deve possuir no mínimo 1 caractere e no máximo 127 caracteres.");
        }

        if (StringUtils.isBlank(this.senha) && StringUtils.isBlank(this.nome) && StringUtils.isBlank(this.sobrenome)) {
            throw new CampoFaltanteException("A senha, nome, sobrenome ou todos os campos mencionados anteriormente devem ser informados para atualizar um usuário.");
        }
    }
}
