package br.dev.wisentini.startthecount.backend.core.model;

import br.dev.wisentini.startthecount.backend.util.StringUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class CabecalhoQRCodeBoletimUrna {

    private MarcaInicioDados marcaInicioDados;

    private VersaoFormatoRepresentacao versaoFormatoRepresentacao;

    private Integer versaoChaveAssinatura;

    public void setMarcaInicioDados(String marcaInicioDados) {
        if (Objects.isNull(marcaInicioDados)) {
            throw new NullPointerException(
                "A marca de início dos dados do boletim de urna deve ser informada."
            );
        }

        this.marcaInicioDados = new MarcaInicioDados(marcaInicioDados);
    }

    public void setVersaoFormatoRepresentacao(String versaoFormatoRepresentacao) {
        if (Objects.isNull(versaoFormatoRepresentacao)) {
            throw new NullPointerException(
                "A versão do formato de representação do boletim de urna deve ser informada."
            );
        }

        this.versaoFormatoRepresentacao = new VersaoFormatoRepresentacao(versaoFormatoRepresentacao);
    }

    public void setVersaoChaveAssinatura(String versaoChaveAssinatura) {
        if (Objects.isNull(versaoChaveAssinatura)) {
            throw new NullPointerException(
                "A versão da chave utilizada para assinar o conteúdo do QR code deve ser informada."
            );
        }

        this.versaoChaveAssinatura = StringUtil.toInteger(versaoChaveAssinatura);
    }
}
