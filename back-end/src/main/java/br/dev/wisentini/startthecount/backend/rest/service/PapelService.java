package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.repository.PapelRepository;
import br.dev.wisentini.startthecount.backend.rest.model.Papel;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PapelService {

    private final PapelRepository papelRepository;

    public Papel findByNome(String nome) {
        return this.papelRepository
            .findByNomeEqualsIgnoreCase(nome)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum papel de usuário com o nome \"%s\".", nome));
            });
    }

    public List<Papel> findAll() {
        return this.papelRepository.findAll();
    }
}
