package br.dev.wisentini.startthecount.backend.core.model.complemento;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class PartidoCargo {

    private List<CandidaturaPartido> candidaturasPorPartidos;

    private Cargo cargo;

    public Map<CandidatoId, Candidato> getCandidatosTitulares() {
        Map<CandidatoId, Candidato> candidatosTitulares = new HashMap<>();

        for (CandidaturaPartido candidaturaPartido : this.candidaturasPorPartidos) {
            for (Map.Entry<CandidatoId, Candidato> entry : candidaturaPartido.getCandidatosTitulares().entrySet()) {
                candidatosTitulares.put(
                    new CandidatoId(entry.getKey().numeroTSECandidato(), this.cargo.getCodigoTSE()),
                    entry.getValue()
                );
            }
        }

        return candidatosTitulares;
    }
}
