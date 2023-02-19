package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosPartidoBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.CargoEleicaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosPartidoBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.ApuracaoVotosPartidoBoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.repository.ApuracaoVotosPartidoBoletimUrnaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApuracaoVotosPartidoBoletimUrnaService {

    private final ApuracaoVotosPartidoBoletimUrnaRepository apuracaoVotosPartidoBoletimUrnaRepository;

    private final ApuracaoVotosPartidoBoletimUrnaMapper apuracaoVotosPartidoBoletimUrnaMapper;

    public ApuracaoVotosPartidoBoletimUrna findById(ApuracaoVotosPartidoBoletimUrnaIdDTO id) {
        return this.apuracaoVotosPartidoBoletimUrnaRepository
            .findByPartidoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
                id.getNumeroTSEPartido(),
                id.getCodigoTSECargo(),
                id.getCodigoTSEEleicao(),
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEPleito()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma apuração de votos de partido de boletim de urna identificada por %s.", id));
            });
    }

    public List<ApuracaoVotosPartidoBoletimUrna> findAll() {
        return this.apuracaoVotosPartidoBoletimUrnaRepository.findAll();
    }

    public ApuracaoVotosPartidoBoletimUrna getIfExistsOrElseSave(ApuracaoVotosPartidoBoletimUrna apuracaoVotosPartidoBoletimUrna) {
        if (this.apuracaoVotosPartidoBoletimUrnaRepository.existsByPartidoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            apuracaoVotosPartidoBoletimUrna.getPartido().getNumeroTSE(),
            apuracaoVotosPartidoBoletimUrna.getCargoEleicao().getCargo().getCodigoTSE(),
            apuracaoVotosPartidoBoletimUrna.getCargoEleicao().getEleicao().getCodigoTSE(),
            apuracaoVotosPartidoBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE(),
            apuracaoVotosPartidoBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            apuracaoVotosPartidoBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            apuracaoVotosPartidoBoletimUrna.getBoletimUrna().getSecaoPleito().getPleito().getCodigoTSE()
        )) {
            return this.findById(this.apuracaoVotosPartidoBoletimUrnaMapper.toApuracaoVotosCargoBoletimUrnaIdDTO(apuracaoVotosPartidoBoletimUrna));
        }

        return this.apuracaoVotosPartidoBoletimUrnaRepository.save(apuracaoVotosPartidoBoletimUrna);
    }

    public void deleteById(ApuracaoVotosPartidoBoletimUrnaIdDTO id) {
        id.validate();

        if (!this.apuracaoVotosPartidoBoletimUrnaRepository.existsByPartidoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getNumeroTSEPartido(),
            id.getCodigoTSECargo(),
            id.getCodigoTSEEleicao(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma apuração de votos de partido de boletim de urna identificada por %s.", id));
        }

        this.apuracaoVotosPartidoBoletimUrnaRepository.deleteByPartidoNumeroTSEAndCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getNumeroTSEPartido(),
            id.getCodigoTSECargo(),
            id.getCodigoTSEEleicao(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );
    }

    public void deleteByPartido(Integer numeroTSEPartido) {
        this.apuracaoVotosPartidoBoletimUrnaRepository.deleteByPartidoNumeroTSE(numeroTSEPartido);
    }

    public void deleteByCargoEleicao(CargoEleicaoIdDTO cargoEleicaoIdDTO) {
        cargoEleicaoIdDTO.validate();

        this.apuracaoVotosPartidoBoletimUrnaRepository
            .deleteByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(
                cargoEleicaoIdDTO.getCodigoTSECargo(),
                cargoEleicaoIdDTO.getCodigoTSEEleicao()
            );
    }

    public void deleteByBoletimUrna(BoletimUrnaIdDTO boletimUrnaId) {
        boletimUrnaId.validate();

        this.apuracaoVotosPartidoBoletimUrnaRepository.deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            boletimUrnaId.getNumeroTSESecao(),
            boletimUrnaId.getNumeroTSEZona(),
            boletimUrnaId.getSiglaUF(),
            boletimUrnaId.getCodigoTSEPleito()
        );
    }
}
