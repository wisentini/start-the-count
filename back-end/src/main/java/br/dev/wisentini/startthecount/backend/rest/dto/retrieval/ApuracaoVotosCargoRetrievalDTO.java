package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

public interface ApuracaoVotosCargoRetrievalDTO {

    Integer getCodigoCargo();

    String getNomeCargo();

    Integer getCodigoEleicao();

    String getNomeEleicao();

    Integer getCodigoPleito();

    Integer getTurno();

    Integer getQuantidadeVotosNominais();

    Integer getQuantidadeVotosDeLegenda();

    Integer getQuantidadeVotosEmBranco();

    Integer getQuantidadeVotosNulos();

    Integer getTotalVotosApurados();
}
