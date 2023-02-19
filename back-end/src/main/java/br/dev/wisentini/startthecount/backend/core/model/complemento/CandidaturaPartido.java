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
public class CandidaturaPartido {

    private Partido partido;

    private List<Candidatura> candidaturas;

    public Map<CandidatoId, Candidato> getCandidatosTitulares() {
        Map<CandidatoId, Candidato> candidatosTitulares = new HashMap<>();

        for (Candidatura candidatura : this.candidaturas) {
            candidatosTitulares.put(
                new CandidatoId(candidatura.getNumeroTSECandidato()),
                candidatura.getTitular()
            );
        }

        return candidatosTitulares;
    }
}
