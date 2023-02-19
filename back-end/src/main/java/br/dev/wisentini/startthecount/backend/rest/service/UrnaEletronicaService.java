package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.repository.UrnaEletronicaRepository;
import br.dev.wisentini.startthecount.backend.rest.model.UrnaEletronica;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrnaEletronicaService {

    private final UrnaEletronicaRepository urnaEletronicaRepository;

    private final BoletimUrnaService boletimUrnaService;

    public UrnaEletronica findByNumeroSerie(Integer numeroSerie) {
        return this.urnaEletronicaRepository
            .findByNumeroSerie(numeroSerie)
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma urna eletrônica com o número de série %d.", numeroSerie));
            });
    }

    public List<UrnaEletronica> findAll() {
        return this.urnaEletronicaRepository.findAll();
    }

    public UrnaEletronica getIfExistsOrElseSave(UrnaEletronica urnaEletronica) {
        if (this.urnaEletronicaRepository.existsByNumeroSerie(urnaEletronica.getNumeroSerie())) {
            return this.findByNumeroSerie(urnaEletronica.getNumeroSerie());
        }

        return this.urnaEletronicaRepository.save(urnaEletronica);
    }

    public void deleteByNumeroSerie(Integer numeroSerie) {
        if (!this.urnaEletronicaRepository.existsByNumeroSerie(numeroSerie)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma urna eletrônica com o número de série %d.", numeroSerie));
        }

        this.boletimUrnaService.deleteByUrnaEletronica(numeroSerie);

        this.urnaEletronicaRepository.deleteByNumeroSerie(numeroSerie);
    }
}
