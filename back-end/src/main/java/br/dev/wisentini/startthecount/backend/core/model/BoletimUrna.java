package br.dev.wisentini.startthecount.backend.core.model;

import br.dev.wisentini.startthecount.backend.core.config.ConfigurationLoader;
import br.dev.wisentini.startthecount.backend.core.exception.*;
import br.dev.wisentini.startthecount.backend.core.service.ChavePublicaVerificacaoAssinaturaCachingService;
import br.dev.wisentini.startthecount.backend.core.service.ComplementoBoletimUrnaCachingService;
import br.dev.wisentini.startthecount.backend.core.util.Security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

@Getter
@Setter
@ToString
public class BoletimUrna {

    private List<QRCodeBoletimUrna> qrCodes;

    private ConteudoQRCodeBoletimUrna conteudo;

    private String assinatura;

    @Getter(value = AccessLevel.NONE)
    private final ChavePublicaVerificacaoAssinaturaCachingService chavePublicaVerificacaoAssinaturaCacheService;

    @Getter(value = AccessLevel.NONE)
    private final ComplementoBoletimUrnaCachingService complementoBoletimUrnaCacheService;

    public BoletimUrna() {
        this.qrCodes = new LinkedList<>();
        this.chavePublicaVerificacaoAssinaturaCacheService = new ChavePublicaVerificacaoAssinaturaCachingService();
        this.complementoBoletimUrnaCacheService = new ComplementoBoletimUrnaCachingService();
    }

    public List<QRCodeBoletimUrna> getQRCodes() {
        return this.qrCodes;
    }

    public void setQRCodes(List<QRCodeBoletimUrna> qrCodes) {
        this.qrCodes = qrCodes;
    }

    public void addQRCode(QRCodeBoletimUrna qrCode) {
        this.qrCodes.add(qrCode);
    }

    public Integer getQuantidadeTotalQRCodes() {
        return this.qrCodes.get(0).getCabecalho().getMarcaInicioDados().getQuantidadeTotalQRCodes();
    }

    private void validarDadosURLChavePublicaVerificacaoAssinatura() throws DadosFaltantesException {
        if (Objects.isNull(this.qrCodes) || this.qrCodes.isEmpty()) {
            throw new QRCodeFaltanteException("O boletim de urna deve ter pelo menos um QR code.");
        }

        Map<String, String> dadosFaltantes = new LinkedHashMap<>();

        QRCodeBoletimUrna primeiroQRCode = this.qrCodes.get(0);

        if (
            Objects.isNull(primeiroQRCode) ||
            Objects.isNull(primeiroQRCode.getCabecalho()) ||
            Objects.isNull(primeiroQRCode.getCabecalho().getVersaoChaveAssinatura())
        ) {
            dadosFaltantes.put("VERSAO_CHAVE", "Versão das chaves de assinatura do(s) QR Code(s).");
        }

        Set<Integer> versoesChaveAssinaturaQRCode = new HashSet<>();

        for (QRCodeBoletimUrna qrCode : this.qrCodes) {
            if (
                versoesChaveAssinaturaQRCode.add(qrCode.getCabecalho().getVersaoChaveAssinatura()) &&
                qrCode.getCabecalho().getMarcaInicioDados().getIndiceQRCode() != 1
            ) {
                throw new VersoesChaveAssinaturaDivergentesException("As versões da chave utilizada para assinar os QR codes do boletim de urna são divergentes.");
            }
        }

        if (
            Objects.isNull(this.conteudo.getProcessoEleitoral()) ||
            Objects.isNull((this.conteudo.getProcessoEleitoral().getOrigemConfiguracao()))
        ) {
            dadosFaltantes.put("ORLC", "Origem da configuração do processo eleitoral.");
        }

        if (Objects.isNull(this.conteudo.getFase()) || Objects.isNull(this.conteudo.getFase().getNomeAbreviado())) {
            dadosFaltantes.put("F", "Primeira letra da fase dos dados em minúsculo.");
        }

        if (Objects.isNull(this.conteudo.getUF())) {
            dadosFaltantes.put("UF", "Sigla da UF em minúsculo.");
        }

        if (!dadosFaltantes.isEmpty()) {
            String mensagem = "Não foi possível compor a URL de verificação da assinatura do boletim de urna porque um ou mais dados que a compõe não foram informados ou são inválidos.";
            throw new DadosFaltantesException(mensagem, dadosFaltantes);
        }
    }

    private String comporURLChavePublicaVerificacaoAssinatura() throws DadosFaltantesException {
        this.validarDadosURLChavePublicaVerificacaoAssinatura();

        Integer versaoChaveAssinaturaQRCode = this.getQRCodes().get(0).getCabecalho().getVersaoChaveAssinatura();
        String origem = this.conteudo.getProcessoEleitoral().getOrigemConfiguracao().toStringURLVerificacaoAssinatura();
        String fase = this.conteudo.getFase().getNomeAbreviado().toLowerCase();
        String UF = this.conteudo.getUF().getSigla().toLowerCase();

        String urlBaseVerificacaoAssinatura = ConfigurationLoader.getConfiguration("core.yaml").getUrls().getVerificacaoAssinatura();

        return String.format("%s/%s/%s/%s%sqrcode.pub", urlBaseVerificacaoAssinatura, versaoChaveAssinaturaQRCode, origem, fase, UF);
    }

    private void validarDadosURLDadosComplementares() throws DadosFaltantesException {
        Map<String, String> dadosFaltantes = new LinkedHashMap<>();

        if (Objects.isNull(this.conteudo.getFase())) {
            dadosFaltantes.put("fase", "Fase dos dados por extenso e em minúsculo.");
        }

        if (Objects.isNull(this.conteudo.getProcessoEleitoral())) {
            dadosFaltantes.put("idProcesso", "Número do processo eleitoral.");
        }

        if (Objects.isNull(this.conteudo.getFase()) || Objects.isNull(this.conteudo.getFase().getNomeAbreviado())) {
            dadosFaltantes.put("F", "Primeira letra da fase dos dados em minúsculo.");
        }

        if (Objects.isNull(this.conteudo.getPleito())) {
            dadosFaltantes.put("ppppp", "Número do pleito com zeros à esquerda, totalizando 5 dígitos.");
        }

        if (Objects.isNull(this.conteudo.getUF())) {
            dadosFaltantes.put("UF", "Sigla da UF em minúsculo.");
        }

        if (Objects.isNull(this.conteudo.getMunicipio())) {
            dadosFaltantes.put("MMMMM", "Número do município com zeros à esquerda, totalizando 5 dígitos.");
        }

        if (!dadosFaltantes.isEmpty()) {
            String mensagem = "Não foi possível compor a URL de busca dos dados complementares do boletim de urna porque um ou mais dados que a compõe não foram informados ou são inválidos.";
            throw new DadosFaltantesException(mensagem, dadosFaltantes);
        }
    }

    private String comporURLDadosComplementares() throws DadosFaltantesException {
        this.validarDadosURLDadosComplementares();

        String fase = this.conteudo.getFase().getNome().toLowerCase();
        String idProcesso = Integer.toString(this.conteudo.getProcessoEleitoral().getCodigoTSE());
        String F = this.conteudo.getFase().getNomeAbreviado().toLowerCase();
        String ppppp = String.format("%05d", this.conteudo.getPleito().getCodigoTSE());
        String UF = this.conteudo.getUF().getSigla().toLowerCase();
        String MMMMMM = String.format("%05d", this.conteudo.getMunicipio().getCodigoTSE());

        String urlBaseDadosComplementares = ConfigurationLoader.getConfiguration("core.yaml").getUrls().getDadosComplementares();

        return String.format("%s/%s/%s/%s%s%s%s-qbu.js", urlBaseDadosComplementares, fase, idProcesso, F, ppppp, UF, MMMMMM);
    }

    public br.dev.wisentini.startthecount.backend.core.model.complemento.BoletimUrna getDadosComplementares() {
        return this.complementoBoletimUrnaCacheService.get(this.comporURLDadosComplementares());
    }

    private void verificarQRCodes() {
        if (Objects.isNull(this.qrCodes) || this.qrCodes.isEmpty()) {
            throw new QRCodeFaltanteException("O boletim de urna deve ter pelo menos um QR code.");
        }

        Iterator<QRCodeBoletimUrna> iterator = this.qrCodes.iterator();

        int ultimoIndice = 0;

        while (iterator.hasNext()) {
            int indice = iterator.next().getCabecalho().getMarcaInicioDados().getIndiceQRCode();

            if (indice <= ultimoIndice) {
                throw new OrdemIncorretaQRCodeBoletimUrnaException("Os QR codes devem ser informados na mesma ordem em que aparecem no boletim de urna.");
            } else if (indice - ultimoIndice != 1) {
                throw new QRCodeFaltanteException(String.format("Está faltando um QR code entre os QR codes %d e %d.", ultimoIndice, indice));
            }

            ultimoIndice = indice;
        }
    }

    private String verificarConteudo() {
        if (Objects.isNull(this.qrCodes) || this.qrCodes.isEmpty()) {
            throw new QRCodeFaltanteException("O boletim de urna deve ter pelo menos um QR code.");
        }

        try {
            String ultimoPayloadConteudo = null;
            String ultimoHashCalculado = null;

            StringJoiner dadosAcumulados = new StringJoiner(" ");

            for (QRCodeBoletimUrna qrCode : this.qrCodes) {
                Integer indice = qrCode.getCabecalho().getMarcaInicioDados().getIndiceQRCode();

                if (indice == 1) {
                    ultimoHashCalculado = Security.sha512(qrCode.getPayloadConteudo());
                } else {
                    dadosAcumulados.add(String.format("%s HASH:%s", ultimoPayloadConteudo, ultimoHashCalculado));
                    ultimoHashCalculado = Security.sha512(String.format("%s %s", dadosAcumulados, qrCode.getPayloadConteudo()));
                }

                if (!ultimoHashCalculado.equalsIgnoreCase(qrCode.getHash())) {
                    throw new HashsDivergentesException(String.format("O hash do QR code %d difere do calculado, ou seja, seu conteúdo foi adulterado.", indice));
                }

                ultimoPayloadConteudo = qrCode.getPayloadConteudo();
            }

            return ultimoHashCalculado;
        } catch (NoSuchAlgorithmException | NoSuchProviderException exception) {
            throw new FalhaVerificacaoConteudoException("Não foi possível apurar o conteúdo do boletim de urna.");
        }
    }

    private void verificarAssinatura() {
        try {
            String url = this.comporURLChavePublicaVerificacaoAssinatura();
            byte[] chavePublica = this.chavePublicaVerificacaoAssinaturaCacheService.get(url);
            String hashUltimoQRCode = this.verificarConteudo();

            if (!Security.verifySignature(hashUltimoQRCode, this.assinatura, chavePublica)) {
                throw new AssinaturasDivergentesException("A assinatura digital do QR code difere da original, ou seja, o conteúdo do boletim de urna foi adulterado.");
            };
        } catch (Exception exception) {
            throw new FalhaVerificacaoAssinaturaException("Não foi possível verificar a assinatura digital do boletim de urna.");
        }
    }

    public void verificar() {
        this.verificarQRCodes();
        this.verificarAssinatura();
    }
}
