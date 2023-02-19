package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.PapelPermissaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Papel;
import br.dev.wisentini.startthecount.backend.rest.model.PapelPermissao;
import br.dev.wisentini.startthecount.backend.rest.model.Permissao;
import br.dev.wisentini.startthecount.backend.rest.repository.PapelPermissaoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PapelPermissaoService {

    private final PapelPermissaoRepository papelPermissaoRepository;

    public PapelPermissao findById(PapelPermissaoIdDTO id) {
        return this.papelPermissaoRepository
            .findByPapelNomeEqualsIgnoreCaseAndPermissaoNomeEqualsIgnoreCase(
                id.getNomePapel(),
                id.getNomePermissao()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma instância de PapelPermissao identificada por %s.", id));
            });
    }

    public List<PapelPermissao> findAll() {
        return this.papelPermissaoRepository.findAll();
    }

    public List<Papel> findPapeisByPermissao(String nomePermissao) {
        return this.papelPermissaoRepository.findPapeisByPermissao(nomePermissao);
    }

    public List<Permissao> findPermissoesByPapel(String nomePapel) {
        return this.papelPermissaoRepository.findPermissoesByPapel(nomePapel);
    }
}
