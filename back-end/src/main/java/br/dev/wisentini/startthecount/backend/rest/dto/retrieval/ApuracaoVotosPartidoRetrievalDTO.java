package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

public interface ApuracaoVotosPartidoRetrievalDTO {

    Integer getNumeroPartido();

    String getNomePartido();

    Integer getCodigoCargo();

    String getNomeCargo();

    Integer getCodigoEleicao();

    String getNomeEleicao();

    Integer getCodigoPleito();

    Integer getTurno();

    Integer getQuantidadeVotosDeLegenda();

    Integer getTotalVotosApurados();
}
