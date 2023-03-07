package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.MunicipioRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Municipio;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MunicipioMapper {

    private final UFMapper ufMapper;

    public MunicipioRetrievalDTO toMunicipioRetrievalDTO(Municipio municipio) {
        return new MunicipioRetrievalDTO(
            municipio.getId(),
            municipio.getCodigoTSE(),
            municipio.getCodigoIBGE(),
            municipio.getNome(),
            this.ufMapper.toUFRetrievalDTO(municipio.getUF())
        );
    }
}
