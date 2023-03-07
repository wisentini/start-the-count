package br.dev.wisentini.startthecount.backend.rest.mapper;

import br.dev.wisentini.startthecount.backend.rest.dto.retrieval.PermissaoRetrievalDTO;
import br.dev.wisentini.startthecount.backend.rest.model.Permissao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissaoMapper {

    public PermissaoRetrievalDTO toPermissaoRetrievalDTO(Permissao permissao) {
        return new PermissaoRetrievalDTO(
            permissao.getId(),
            permissao.getNome(),
            permissao.getDescricao()
        );
    }
}
