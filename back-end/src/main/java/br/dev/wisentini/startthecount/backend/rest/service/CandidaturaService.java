package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.CandidaturaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.CargoEleicaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.CandidaturaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Candidatura;
import br.dev.wisentini.startthecount.backend.rest.repository.CandidaturaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidaturaService {

    private final CandidaturaRepository candidaturaRepository;

    private final CandidaturaMapper candidaturaMapper;

    private final ApuracaoVotosCandidaturaBoletimUrnaService apuracaoVotosCandidaturaBoletimUrnaService;

    public Candidatura findById(CandidaturaIdDTO id) {
        return this.candidaturaRepository
            .findByCandidatoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(
                id.getNumeroTSECandidato(),
                id.getCodigoTSECargo(),
                id.getCodigoTSEEleicao()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma candidatura identificada por %s.", id));
            });
    }

    public List<Candidatura> findAll() {
        return this.candidaturaRepository.findAll();
    }

    public List<Candidatura> findByPartido(Integer numeroTSEPartido) {
        return this.candidaturaRepository.findByPartidoNumeroTSE(numeroTSEPartido);
    }

    public Candidatura getIfExistsOrElseSave(Candidatura candidatura) {
        if (this.candidaturaRepository.existsByCandidatoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(
            candidatura.getCandidato().getNumeroTSE(),
            candidatura.getCargoEleicao().getCargo().getCodigoTSE(),
            candidatura.getCargoEleicao().getEleicao().getCodigoTSE()
        )) {
            return this.findById(this.candidaturaMapper.toCandidaturaIdDTO(candidatura));
        }

        return this.candidaturaRepository.save(candidatura);
    }

    public void deleteById(CandidaturaIdDTO id) {
        id.validate();

        if (!this.candidaturaRepository.existsByCandidatoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(
            id.getNumeroTSECandidato(),
            id.getCodigoTSECargo(),
            id.getCodigoTSEEleicao()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma candidatura identificada por %s.", id));
        }

        this.apuracaoVotosCandidaturaBoletimUrnaService.deleteByCandidatura(id);

        this.candidaturaRepository.deleteByCandidatoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(
            id.getNumeroTSECandidato(),
            id.getCodigoTSECargo(),
            id.getCodigoTSEEleicao()
        );
    }

    public void deleteByCargoEleicao(CargoEleicaoIdDTO cargoEleicaoId) {
        cargoEleicaoId.validate();

        this.candidaturaRepository
            .findByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(cargoEleicaoId.getCodigoTSECargo(), cargoEleicaoId.getCodigoTSEEleicao())
            .forEach(candidatura -> this.apuracaoVotosCandidaturaBoletimUrnaService.deleteByCandidatura(
                this.candidaturaMapper.toCandidaturaIdDTO(candidatura)
            ));

        this.candidaturaRepository.deleteByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(cargoEleicaoId.getCodigoTSECargo(), cargoEleicaoId.getCodigoTSEEleicao());
    }

    public void deleteByPartido(int numeroTSEPartido) {
        this.candidaturaRepository
            .findByPartidoNumeroTSE(numeroTSEPartido)
            .forEach(candidatura -> this.apuracaoVotosCandidaturaBoletimUrnaService.deleteByCandidatura(
                this.candidaturaMapper.toCandidaturaIdDTO(candidatura)
            ));

        this.candidaturaRepository.deleteByPartidoNumeroTSE(numeroTSEPartido);
    }

    public void deleteByCandidato(String codigoTSECandidato) {
        this.candidaturaRepository
            .findByCandidatoCodigoTSEEqualsIgnoreCase(codigoTSECandidato)
            .forEach(candidatura -> this.apuracaoVotosCandidaturaBoletimUrnaService.deleteByCandidatura(
                this.candidaturaMapper.toCandidaturaIdDTO(candidatura)
            ));

        this.candidaturaRepository.deleteByCandidatoCodigoTSEEqualsIgnoreCase(codigoTSECandidato);
    }
}
