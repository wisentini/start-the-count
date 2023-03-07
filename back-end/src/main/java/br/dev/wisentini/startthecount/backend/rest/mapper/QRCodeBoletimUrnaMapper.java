package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.QRCodeBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.QRCodeBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.QRCodeBoletimUrna;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QRCodeBoletimUrnaMapper {

    private final BoletimUrnaMapper boletimUrnaMapper;

    public QRCodeBoletimUrnaRetrievalDTO toQRCodeBoletimUrnaRetrievalDTO(QRCodeBoletimUrna qrCodeBoletimUrna) {
        return new QRCodeBoletimUrnaRetrievalDTO(
            qrCodeBoletimUrna.getId(),
            this.boletimUrnaMapper.toBoletimUrnaRetrievalDTO(qrCodeBoletimUrna.getBoletimUrna()),
            qrCodeBoletimUrna.getCabecalho(),
            qrCodeBoletimUrna.getConteudo(),
            qrCodeBoletimUrna.getHash(),
            qrCodeBoletimUrna.getIndice(),
            qrCodeBoletimUrna.getNumeroCiclosEleitoraisDesdeImplementacao(),
            qrCodeBoletimUrna.getNumeroRevisoesFormatoCiclo(),
            qrCodeBoletimUrna.getNumeroVersaoChaveAssinatura()
        );
    }

    public QRCodeBoletimUrnaIdDTO toQRCodeBoletimUrnaIdDTO(QRCodeBoletimUrna qrCodeBoletimUrna) {
        return new QRCodeBoletimUrnaIdDTO(
            qrCodeBoletimUrna.getIndice(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            qrCodeBoletimUrna.getBoletimUrna().getSecaoPleito().getPleito().getCodigoTSE()
        );
    }
}
