package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosCargoBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.ApuracaoVotosCargoBoletimUrnaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.ApuracaoVotosCargoBoletimUrna;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApuracaoVotosCargoBoletimUrnaMapper {

    private final CargoEleicaoMapper cargoEleicaoMapper;

    private final BoletimUrnaMapper boletimUrnaMapper;

    public ApuracaoVotosCargoBoletimUrnaRetrievalDTO toApuracaoVotosCargoBoletimUrnaRetrievalDTO(ApuracaoVotosCargoBoletimUrna apuracaoVotosCargoBoletimUrna) {
        return new ApuracaoVotosCargoBoletimUrnaRetrievalDTO(
            apuracaoVotosCargoBoletimUrna.getId(),
            this.cargoEleicaoMapper.toCargoEleicaoRetrievalDTO(apuracaoVotosCargoBoletimUrna.getCargoEleicao()),
            this.boletimUrnaMapper.toBoletimUrnaRetrievalDTO(apuracaoVotosCargoBoletimUrna.getBoletimUrna()),
            apuracaoVotosCargoBoletimUrna.getQuantidadeEleitoresAptos(),
            apuracaoVotosCargoBoletimUrna.getQuantidadeComparecimentoCargoSemCandidatos(),
            apuracaoVotosCargoBoletimUrna.getQuantidadeVotosNominais(),
            apuracaoVotosCargoBoletimUrna.getQuantidadeVotosDeLegenda(),
            apuracaoVotosCargoBoletimUrna.getQuantidadeVotosEmBranco(),
            apuracaoVotosCargoBoletimUrna.getQuantidadeVotosNulos(),
            apuracaoVotosCargoBoletimUrna.getTotalVotosApurados()
        );
    }

    public ApuracaoVotosCargoBoletimUrnaIdDTO toApuracaoVotosCargoBoletimUrnaIdDTO(ApuracaoVotosCargoBoletimUrna apuracaoVotosCargoBoletimUrna) {
        return new ApuracaoVotosCargoBoletimUrnaIdDTO(
            apuracaoVotosCargoBoletimUrna.getCargoEleicao().getCargo().getCodigoTSE(),
            apuracaoVotosCargoBoletimUrna.getCargoEleicao().getEleicao().getCodigoTSE(),
            apuracaoVotosCargoBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE(),
            apuracaoVotosCargoBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            apuracaoVotosCargoBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            apuracaoVotosCargoBoletimUrna.getBoletimUrna().getSecaoPleito().getPleito().getCodigoTSE()
        );
    }
}
