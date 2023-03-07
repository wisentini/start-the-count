package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.UrnaEletronicaRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.UrnaEletronica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrnaEletronicaMapper {

    public UrnaEletronicaRetrievalDTO toUrnaEletronicaRetrievalDTO(UrnaEletronica urnaEletronica) {
        return new UrnaEletronicaRetrievalDTO(
            urnaEletronica.getId(),
            urnaEletronica.getNumeroSerie(),
            urnaEletronica.getCodigoIdentificacaoCarga(),
            urnaEletronica.getVersaoSoftware(),
            urnaEletronica.getDataAbertura(),
            urnaEletronica.getHorarioAbertura(),
            urnaEletronica.getDataFechamento(),
            urnaEletronica.getHorarioFechamento()
        );
    }
}
