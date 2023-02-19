package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosCandidaturaBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.ApuracaoVotosCandidaturaBoletimUrna;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApuracaoVotosCandidaturaBoletimUrnaMapper {

    private final CandidaturaMapper candidaturaMapper;

    private final BoletimUrnaMapper boletimUrnaMapper;

    public ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO toApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO(ApuracaoVotosCandidaturaBoletimUrna apuracaoVotosCandidaturaBoletimUrna) {
        return new ApuracaoVotosCandidaturaBoletimUrnaRetrievalDTO(
            apuracaoVotosCandidaturaBoletimUrna.getId(),
            this.candidaturaMapper.toCandidaturaRetrievalDTO(apuracaoVotosCandidaturaBoletimUrna.getCandidatura()),
            this.boletimUrnaMapper.toBoletimUrnaRetrievalDTO(apuracaoVotosCandidaturaBoletimUrna.getBoletimUrna()),
            apuracaoVotosCandidaturaBoletimUrna.getTotalVotosApurados()
        );
    }

    public ApuracaoVotosCandidaturaBoletimUrnaIdDTO toApuracaoVotosCandidaturaBoletimUrnaIdDTO(ApuracaoVotosCandidaturaBoletimUrna apuracaoVotosCandidaturaBoletimUrna) {
        return new ApuracaoVotosCandidaturaBoletimUrnaIdDTO(
            apuracaoVotosCandidaturaBoletimUrna.getCandidatura().getCandidato().getNumeroTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getCandidatura().getCargoEleicao().getCargo().getCodigoTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getCandidatura().getCargoEleicao().getEleicao().getCodigoTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            apuracaoVotosCandidaturaBoletimUrna.getBoletimUrna().getSecaoPleito().getPleito().getCodigoTSE()
        );
    }
}
