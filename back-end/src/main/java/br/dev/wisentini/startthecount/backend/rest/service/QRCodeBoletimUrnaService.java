package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.QRCodeBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.repository.QRCodeBoletimUrnaRepository;
import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.QRCodeBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.model.QRCodeBoletimUrna;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "qr-code-boletim-urna")
public class QRCodeBoletimUrnaService {

    private final QRCodeBoletimUrnaRepository qrCodeBoletimUrnaRepository;

    private final QRCodeBoletimUrnaMapper qrCodeBoletimUrnaMapper;

    private final CachingService cachingService;

    @Cacheable(key = "T(java.lang.String).format('%d:%d:%d:%s:%d', #id.indice, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
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

    @Cacheable(key = "#root.methodName")
    public List<QRCodeBoletimUrna> findAll() {
        return this.qrCodeBoletimUrnaRepository.findAll();
    }

    public QRCodeBoletimUrna getIfExistsOrElseSave(QRCodeBoletimUrna qrCodeBoletimUrna) {
        if (this.qrCodeBoletimUrnaRepository.existsByIndiceAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            qrCodeBoletimUrna.getIndice(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getPleito().getCodigoTSE()
        )) {
            return this.findById(this.qrCodeBoletimUrnaMapper.toQRCodeBoletimUrnaIdDTO(qrCodeBoletimUrna));
        }

        this.cachingService.evictAllCaches();

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

        this.cachingService.evictAllCaches();
    }

    public void deleteByBoletimUrna(BoletimUrnaIdDTO boletimUrnaId) {
        boletimUrnaId.validate();

        this.qrCodeBoletimUrnaRepository.deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            boletimUrnaId.getNumeroTSESecao(),
            boletimUrnaId.getNumeroTSEZona(),
            boletimUrnaId.getSiglaUF(),
            boletimUrnaId.getCodigoTSEPleito()
        );

        this.cachingService.evictAllCaches();
    }
}
