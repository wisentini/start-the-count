package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.Fase;
import br.dev.wisentini.startthecount.backend.rest.repository.FaseRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaseService {

    private final FaseRepository faseRepository;

    private final BoletimUrnaService boletimUrnaService;

    public Fase findByNome(String nome) {
        return this.faseRepository
            .findByNomeEqualsIgnoreCase(nome)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma fase com o nome \"%s\".", nome));
            });
    }

    public List<Fase> findAll() {
        return this.faseRepository.findAll();
    }

    public Fase getIfExistsOrElseSave(Fase fase) {
        if (this.faseRepository.existsByNomeEqualsIgnoreCase(fase.getNome())) {
            return this.findByNome(fase.getNome());
        }

        return this.faseRepository.save(fase);
    }

    public void deleteByNome(String nome) {
        if (!this.faseRepository.existsByNomeEqualsIgnoreCase(nome)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma fase com o nome \"%s\".", nome));
        }

        this.boletimUrnaService.deleteByFase(nome);

        this.faseRepository.deleteByNomeEqualsIgnoreCase(nome);
    }
}
