package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.service.CandidatoService;
import br.dev.wisentini.startthecount.backend.rest.dto.id.CandidaturaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CandidaturaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Candidatura;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidaturaMapper {

    private final CandidatoMapper candidatoMapper;

    private final CargoEleicaoMapper cargoEleicaoMapper;

    private final PartidoMapper partidoMapper;

    private final CandidatoService candidatoService;

    public CandidaturaRetrievalDTO toCandidaturaRetrievalDTO(Candidatura candidatura) {
        return new CandidaturaRetrievalDTO(
            candidatura.getId(),
            this.candidatoMapper.toCandidatoRetrievalDTO(
                this.candidatoService.findByCodigoTSE(candidatura.getCandidato().getCodigoTSE())
            ),
            this.cargoEleicaoMapper.toCargoEleicaoRetrievalDTO(candidatura.getCargoEleicao()),
            this.partidoMapper.toPartidoRetrievalDTO(candidatura.getPartido())
        );
    }

    public CandidaturaIdDTO toCandidaturaIdDTO(Candidatura candidatura) {
        return new CandidaturaIdDTO(
            candidatura.getCandidato().getNumeroTSE(),
            candidatura.getCargoEleicao().getCargo().getCodigoTSE(),
            candidatura.getCargoEleicao().getEleicao().getCodigoTSE()
        );
    }
}
