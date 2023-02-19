package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

public interface ApuracaoVotosCandidaturaRetrievalDTO {

    Integer getNumeroCandidato();

    String getNomeCandidato();

    Integer getCodigoCargo();

    String getNomeCargo();

    Integer getCodigoEleicao();

    String getNomeEleicao();

    Integer getCodigoPleito();

    Integer getTurno();

    Integer getTotalVotosApurados();
}
