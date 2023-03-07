package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.CandidatoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Candidato;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidatoMapper {

    public CandidatoRetrievalDTO toCandidatoRetrievalDTO(Candidato candidato) {
        return new CandidatoRetrievalDTO(
            candidato.getId(),
            candidato.getNumeroTSE(),
            candidato.getCodigoTSE(),
            candidato.getNome()
        );
    }
}
