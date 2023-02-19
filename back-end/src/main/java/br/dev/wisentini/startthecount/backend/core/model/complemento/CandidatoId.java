package br.dev.wisentini.startthecount.backend.core.model.complemento;

public record CandidatoId(Integer numeroTSECandidato, Integer codigoTSECargo) {

    public CandidatoId(Integer numeroTSECandidato) {
        this(numeroTSECandidato, null);
    }
}
