package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeJaExisteException;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.PapelUsuario;
import br.dev.wisentini.startthecount.backend.rest.repository.UsuarioRepository;
import br.dev.wisentini.startthecount.backend.rest.dto.creation.UsuarioCreationDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.update.UsuarioUpdateDTO;
import br.dev.wisentini.startthecount.backend.rest.mapper.UsuarioMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "usuario")
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final PapelService papelService;

    private final PapelUsuarioService papelUsuarioService;

    private final BoletimUrnaUsuarioService boletimUrnaUsuarioService;

    @Autowired
    public UsuarioService(
        UsuarioRepository usuarioRepository,
        UsuarioMapper usuarioMapper,
        PapelService papelService,
        PapelUsuarioService papelUsuarioService,
        @Lazy BoletimUrnaUsuarioService boletimUrnaUsuarioService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.papelService = papelService;
        this.papelUsuarioService = papelUsuarioService;
        this.boletimUrnaUsuarioService = boletimUrnaUsuarioService;
    }

    @Cacheable(key = "#username")
    public Usuario findByUsername(String username) {
        return this.usuarioRepository
            .findByUsernameEqualsIgnoreCase(username)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum usuário com o username \"%s\".", username));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }

    @CachePut(key = "#username")
    public void updateByUsername(String username, UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuario = this.usuarioRepository
            .findByUsernameEqualsIgnoreCase(username)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum usuário com o username \"%s\".", username));
            });

        usuarioUpdateDTO.validate();

        usuario.update(this.usuarioMapper.toUsuario(usuarioUpdateDTO));

        this.usuarioRepository.save(usuario);
    }

    @CacheEvict(allEntries = true)
    public Usuario save(UsuarioCreationDTO usuarioCreationDTO) {
        usuarioCreationDTO.validate();

        if (this.usuarioRepository.existsByUsernameEqualsIgnoreCase(usuarioCreationDTO.getUsername())) {
            throw new EntidadeJaExisteException(String.format("Já existe um usuário com o username \"%s\".", usuarioCreationDTO.getUsername()));
        }

        Usuario usuario = this.usuarioRepository.save(this.usuarioMapper.toUsuario(usuarioCreationDTO));

        usuarioCreationDTO.getNomesTiposUsuario().forEach(nomePapel -> this.papelUsuarioService.save(new PapelUsuario(
            usuario,
            this.papelService.findByNome(nomePapel)
        )));

        return usuario;
    }

    @Cacheable(key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usuarioRepository
            .findByUsernameEqualsIgnoreCase(username)
            .orElseThrow(() -> {
                throw new UsernameNotFoundException(String.format("Não foi encontrado nenhum usuário com o username \"%s\".", username));
            });
    }

    @CacheEvict(allEntries = true)
    public void deleteByUsername(String username) {
        if (!this.usuarioRepository.existsByUsernameEqualsIgnoreCase(username)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum usuário com o username \"%s\".", username));
        }

        this.boletimUrnaUsuarioService.deleteByUsuario(username);

        this.usuarioRepository.deleteByUsernameEqualsIgnoreCase(username);
    }
}
