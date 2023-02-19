package br.dev.wisentini.startthecount.backend.core.service;

import br.dev.wisentini.startthecount.backend.core.exception.EstruturaIncorretaPayloadQRCodeBoletimUrnaException;
import br.dev.wisentini.startthecount.backend.core.model.BoletimUrna;
import br.dev.wisentini.startthecount.backend.core.model.CabecalhoQRCodeBoletimUrna;
import br.dev.wisentini.startthecount.backend.core.model.ConteudoQRCodeBoletimUrna;
import br.dev.wisentini.startthecount.backend.core.model.QRCodeBoletimUrna;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@NoArgsConstructor
public class BoletimUrnaBuilder {

    private static final Set<String> KEYS = Set.of(
        "^QRBU$",   "^VRQR$", "^VRCH$", "^ORIG$", "^ORLC$",
        "^PROC$",   "^DTPL$", "^PLEI$", "^TURN$", "^FASE$",
        "^UNFE$",   "^MUNI$", "^ZONA$", "^SECA$", "^AGRE$",
        "^IDUE$",   "^IDCA$", "^VERS$", "^LOCA$", "^APTO$",
        "^COMP$",   "^FALT$", "^HBMA$", "^DTAB$", "^HRAB$",
        "^DTFC$",   "^HRFC$", "^JUNT$", "^TURM$", "^DTEM$",
        "^HREM$",   "^IDEL$", "^MAJO$", "^PROP$", "^CARG$",
        "^TIPO$",   "^VERC$", "^PART$", "^LEGP$", "^TOTP$",
        "^[0-9]*$", "^APTA$", "^CSEC$", "^NOMI$", "^LEGC$",
        "^BRAN$",   "^NULO$", "^TOTC$", "^HASH$", "^ASSI$"
    );

    private static Set<String> extrairChaves(String[] registros) {
        Set<String> chaves = new HashSet<>();

        for (String registro : registros) {
            chaves.add(registro.split(":", 2)[0]);
        }

        return chaves;
    }

    private static boolean chaveCorrespondeAoPadrao(String chave, String padrao) {
        return Pattern.compile(padrao, Pattern.CASE_INSENSITIVE).matcher(chave).matches();
    }
    
    private static void verificarChave(String chave) {
        boolean chaveExiste = false;

        Iterator<String> iterador = KEYS.iterator();

        while (iterador.hasNext() && !chaveExiste) {
            if (chaveCorrespondeAoPadrao(chave, iterador.next())) {
                chaveExiste = true;
            }
        }

        if (!chaveExiste) {
            String mensagem = String.format("A estrutura do payload de um QR code de boletim de urna não contém nenhuma chave \"%s\".", chave);
            throw new EstruturaIncorretaPayloadQRCodeBoletimUrnaException(mensagem);
        }
    }

    private static void verificarChaves(Set<String> chaves) {
        for (String chave : chaves) {
            verificarChave(chave);
        }
    }

    private static void verificarRegistros(String[] registros) {
        verificarChaves(extrairChaves(registros));
    }

    public static BoletimUrna build(List<String> payloads) {
        BoletimUrna boletimUrna = new BoletimUrna();
        ConteudoQRCodeBoletimUrna conteudoQRCodeBoletimUrna = new ConteudoQRCodeBoletimUrna();

        for (String payload : payloads) {
            QRCodeBoletimUrna qrCodeBoletimUrna = new QRCodeBoletimUrna();
            CabecalhoQRCodeBoletimUrna cabecalhoQRCodeBoletimUrna = new CabecalhoQRCodeBoletimUrna();

            String[] registros = payload.split("\\s+");

            verificarRegistros(registros);

            for (String registro: registros) {
                String[] chaveValor = registro.split(":", 2);
                String chave = chaveValor[0];
                String valor = chaveValor[1];

                if (chaveCorrespondeAoPadrao(chave, "^QRBU$")) {
                    cabecalhoQRCodeBoletimUrna.setMarcaInicioDados(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^VRQR$")) {
                    cabecalhoQRCodeBoletimUrna.setVersaoFormatoRepresentacao(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^VRCH$")) {
                    cabecalhoQRCodeBoletimUrna.setVersaoChaveAssinatura(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^ORIG$")) {
                    conteudoQRCodeBoletimUrna.setOrigem(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^ORLC$")) {
                    conteudoQRCodeBoletimUrna.setOrigemConfiguracaoProcessoEleitoral(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^PROC$")) {
                    conteudoQRCodeBoletimUrna.setCodigoTSEProcessoEleitoral(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^DTPL$")) {
                    conteudoQRCodeBoletimUrna.setDataPleito(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^PLEI$")) {
                    conteudoQRCodeBoletimUrna.setCodigoTSEPleito(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^TURN$")) {
                    conteudoQRCodeBoletimUrna.setTurnoPleito(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^FASE$")) {
                    conteudoQRCodeBoletimUrna.setFase(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^UNFE$")) {
                    conteudoQRCodeBoletimUrna.setUF(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^MUNI$")) {
                    conteudoQRCodeBoletimUrna.setCodigoTSEMunicipio(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^ZONA$")) {
                    conteudoQRCodeBoletimUrna.setNumeroTSEZona(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^SECA$")) {
                    conteudoQRCodeBoletimUrna.setNumeroTSESecao(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^AGRE$")) {
                    conteudoQRCodeBoletimUrna.setSecoesAgregadas(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^IDUE$")) {
                    conteudoQRCodeBoletimUrna.setNumeroSerieUrnaEletronica(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^IDCA$")) {
                    conteudoQRCodeBoletimUrna.setCodigoIdentificacaoCargaUrnaEletronica(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^VERS$")) {
                    conteudoQRCodeBoletimUrna.setVersaoSoftwareUrnaEletronica(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^LOCA$")) {
                    conteudoQRCodeBoletimUrna.setNumeroTSELocalVotacao(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^APTO$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeEleitoresAptosSecaoPleito(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^COMP$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeEleitoresComparecentesSecaoPleito(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^FALT$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeEleitoresFaltososSecaoPleito(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^HBMA$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeEleitoresHabilitadosPorAnoNascimentoSecaoPleito(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^DTAB$")) {
                    conteudoQRCodeBoletimUrna.setDataAberturaUrnaEletronica(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^HRAB$")) {
                    conteudoQRCodeBoletimUrna.setHorarioAberturaUrnaEletronica(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^DTFC$")) {
                    conteudoQRCodeBoletimUrna.setDataFechamentoUrnaEletronica(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^HRFC$")) {
                    conteudoQRCodeBoletimUrna.setHorarioFechamentoUrnaEletronica(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^JUNT$")) {
                    conteudoQRCodeBoletimUrna.setNumeroJuntaApuradora(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^TURM$")) {
                    conteudoQRCodeBoletimUrna.setNumeroTurmaApuradora(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^DTEM$")) {
                    conteudoQRCodeBoletimUrna.setDataEmissaoBoletimUrna(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^HREM$")) {
                    conteudoQRCodeBoletimUrna.setHorarioEmissaoBoletimUrna(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^IDEL$")) {
                    conteudoQRCodeBoletimUrna.addEleicao(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^MAJO$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeVotosCargosMajoritariosEleicao(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^PROP$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeVotosCargosProporcionaisEleicao(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^CARG$")) {
                    conteudoQRCodeBoletimUrna.addCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^TIPO$")) {
                    conteudoQRCodeBoletimUrna.setTipoCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^VERC$")) {
                    conteudoQRCodeBoletimUrna.setVersaoPacoteDadosCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^PART$")) {
                    conteudoQRCodeBoletimUrna.addPartido(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^LEGP$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeVotosDeLegendaPartido(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^TOTP$")) {
                    conteudoQRCodeBoletimUrna.setTotalVotosApuradosPartido(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^[0-9]*$")) {
                    conteudoQRCodeBoletimUrna.addCandidatura(chave, valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^APTA$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeEleitoresAptosCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^CSEC$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeComparecimentoCargoSemCandidatosCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^NOMI$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeVotosNominaisCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^LEGC$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeVotosDeLegendaCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^BRAN$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeVotosEmBrancoCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^NULO$")) {
                    conteudoQRCodeBoletimUrna.setQuantidadeVotosNulosCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^TOTC$")) {
                    conteudoQRCodeBoletimUrna.setTotalVotosApuradosCargo(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^HASH$")) {
                    qrCodeBoletimUrna.setHash(valor);
                } else if (chaveCorrespondeAoPadrao(chave, "^ASSI$")) {
                    boletimUrna.setAssinatura(valor);
                    qrCodeBoletimUrna.setAssinatura(valor);
                }
            }

            qrCodeBoletimUrna.setPayload(payload);
            qrCodeBoletimUrna.setCabecalho(cabecalhoQRCodeBoletimUrna);
            boletimUrna.addQRCode(qrCodeBoletimUrna);
        }

        conteudoQRCodeBoletimUrna.finalizarMapeamentoRelacionamentos();

        boletimUrna.setConteudo(conteudoQRCodeBoletimUrna);

        boletimUrna.verificar();

        return boletimUrna;
    }
}
