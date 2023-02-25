package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.creation.UsuarioCreationDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UsuarioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.update.UsuarioUpdateDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioMapper(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario toUsuario(UsuarioUpdateDTO usuarioUpdateDTO) {
        return new Usuario(
            null,
            this.passwordEncoder.encode(usuarioUpdateDTO.getSenha()),
            usuarioUpdateDTO.getNome(),
            usuarioUpdateDTO.getSobrenome()
        );
    }

    public Usuario toUsuario(UsuarioCreationDTO usuarioCreationDTO) {
        return new Usuario(
            usuarioCreationDTO.getUsername(),
            this.passwordEncoder.encode(usuarioCreationDTO.getSenha()),
            usuarioCreationDTO.getNome(),
            usuarioCreationDTO.getSobrenome()
        );
    }

    public UsuarioRetrievalDTO toUsuarioRetrievalDTO(Usuario usuario) {
        return new UsuarioRetrievalDTO(
            usuario.getId(),
            usuario.getUsername(),
            usuario.getNome(),
            usuario.getSobrenome(),
            usuario.getNomesPapeis(),
            usuario.getCriadoEm(),
            usuario.getAtualizadoEm()
        );
    }
}
