package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.repository.PermissaoRepository;
import br.dev.wisentini.startthecount.backend.rest.model.Permissao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;

    public Permissao findByNome(String nome) {
        return this.permissaoRepository
            .findByNomeEqualsIgnoreCase(nome)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma permissao com o nome \"%s\".", nome));
            });
    }

    public List<Permissao> findAll() {
        return this.permissaoRepository.findAll();
    }
}
