package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.creation.UsuarioCreationDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.update.UsuarioUpdateDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeJaExisteException;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.UsuarioMapper;
import br.dev.wisentini.startthecount.backend.rest.model.*;
import br.dev.wisentini.startthecount.backend.rest.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "usuario")
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final PapelService papelService;

    private final PapelUsuarioService papelUsuarioService;

    private final BoletimUrnaUsuarioService boletimUrnaUsuarioService;

    private final CachingService cachingService;

    @Autowired
    public UsuarioService(
        UsuarioRepository usuarioRepository,
        UsuarioMapper usuarioMapper,
        PapelService papelService,
        PapelUsuarioService papelUsuarioService,
        @Lazy BoletimUrnaUsuarioService boletimUrnaUsuarioService,
        CachingService cachingService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.papelService = papelService;
        this.papelUsuarioService = papelUsuarioService;
        this.boletimUrnaUsuarioService = boletimUrnaUsuarioService;
        this.cachingService = cachingService;
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

    @Cacheable(key = "T(java.lang.String).format('%s:%s', #root.methodName, #username)")
    public Set<Papel> findPapeis(String username) {
        return this
            .findByUsername(username)
            .getPapeis()
            .stream()
            .map(PapelUsuario::getPapel)
            .collect(Collectors.toSet());
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%s', #root.methodName, #username)")
    public Set<BoletimUrna> findBoletinsUrna(String username) {
        return this
            .findByUsername(username)
            .getBoletinsUrna()
            .stream()
            .map(BoletimUrnaUsuario::getBoletimUrna)
            .collect(Collectors.toSet());
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

    public Usuario save(UsuarioCreationDTO usuarioCreationDTO) {
        usuarioCreationDTO.validate();

        if (this.usuarioRepository.existsByUsernameEqualsIgnoreCase(usuarioCreationDTO.getUsername())) {
            throw new EntidadeJaExisteException(String.format("Já existe um usuário com o username \"%s\".", usuarioCreationDTO.getUsername()));
        }

        Usuario usuario = this.usuarioRepository.save(this.usuarioMapper.toUsuario(usuarioCreationDTO));

        usuarioCreationDTO.getNomesPapeis().forEach(nomePapel -> this.papelUsuarioService.save(new PapelUsuario(
            usuario,
            this.papelService.findByNome(nomePapel)
        )));

        this.cachingService.evictAllCaches();

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

    public void deleteByUsername(String username) {
        if (!this.usuarioRepository.existsByUsernameEqualsIgnoreCase(username)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum usuário com o username \"%s\".", username));
        }

        this.boletimUrnaUsuarioService.deleteByUsuario(username);
        this.papelUsuarioService.deleteByUsuario(username);

        this.usuarioRepository.deleteByUsernameEqualsIgnoreCase(username);

        this.cachingService.evictAllCaches();
    }
}
