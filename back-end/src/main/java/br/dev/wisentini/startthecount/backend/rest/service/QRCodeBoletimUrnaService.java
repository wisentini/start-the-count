package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.QRCodeBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.repository.QRCodeBoletimUrnaRepository;
import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.QRCodeBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.model.QRCodeBoletimUrna;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QRCodeBoletimUrnaService {

    private final QRCodeBoletimUrnaRepository qrCodeBoletimUrnaRepository;

    private final QRCodeBoletimUrnaMapper qrCodeBoletimUrnaMapper;

    public QRCodeBoletimUrna findById(QRCodeBoletimUrnaIdDTO id) {
        return this.qrCodeBoletimUrnaRepository
            .findByIndiceAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
                id.getIndice(),
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEPleito()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum QR code de boletim de urna identificado por %s.", id));
            });
    }

    public List<QRCodeBoletimUrna> findAll() {
        return this.qrCodeBoletimUrnaRepository.findAll();
    }

    public List<QRCodeBoletimUrna> findByBoletimUrna(BoletimUrnaIdDTO id) {
        id.validate();

        return this.qrCodeBoletimUrnaRepository.findByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );
    }

    public QRCodeBoletimUrna getIfExistsOrElseSave(QRCodeBoletimUrna qrCodeBoletimUrna) {
        if (this.qrCodeBoletimUrnaRepository.existsByIndiceAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            qrCodeBoletimUrna.getIndice(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getPleito().getCodigoTSE()
        )) {
            return this.findById(this.qrCodeBoletimUrnaMapper.toQrCodeBoletimUrnaIdDTO(qrCodeBoletimUrna));
        }

        return this.qrCodeBoletimUrnaRepository.save(qrCodeBoletimUrna);
    }

    public void deleteById(QRCodeBoletimUrnaIdDTO id) {
        id.validate();

        if (!this.qrCodeBoletimUrnaRepository.existsByIndiceAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getIndice(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum QR code de boletim de urna identificado por %s.", id));
        }

        this.qrCodeBoletimUrnaRepository.deleteByIndiceAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getIndice(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );
    }

    public void deleteByBoletimUrna(BoletimUrnaIdDTO boletimUrnaId) {
        boletimUrnaId.validate();

        this.qrCodeBoletimUrnaRepository.deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            boletimUrnaId.getNumeroTSESecao(),
            boletimUrnaId.getNumeroTSEZona(),
            boletimUrnaId.getSiglaUF(),
            boletimUrnaId.getCodigoTSEPleito()
        );
    }
}
