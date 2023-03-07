package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.EleicaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EleicaoMapper {

    private final PleitoMapper pleitoMapper;

    public EleicaoRetrievalDTO toEleicaoRetrievalDTO(Eleicao eleicao) {
        return new EleicaoRetrievalDTO(
            eleicao.getId(),
            eleicao.getCodigoTSE(),
            eleicao.getNome(),
            eleicao.getAno(),
            eleicao.getQuantidadeVotosCargosMajoritarios(),
            eleicao.getQuantidadeVotosCargosProporcionais(),
            this.pleitoMapper.toPleitoRetrievalDTO(eleicao.getPleito())
        );
    }
}
