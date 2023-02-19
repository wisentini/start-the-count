package br.dev.wisentini.startthecount.backend.core.service;

import br.dev.wisentini.startthecount.backend.core.exception.FalhaRequisicaoWebException;
import br.dev.wisentini.startthecount.backend.core.util.WebRequest;

import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;

public class ChavePublicaVerificacaoAssinaturaCacheService extends CacheService<byte[]> {

    public byte[] get(String url) {
        try {
            if (redis.exists(url)) {
                return Hex.decode(redis.get(url));
            }

            byte[] chavePublica = WebRequest.getBytes(url);

            redis.set(url, Hex.toHexString(chavePublica).toUpperCase());

            return chavePublica;
        } catch (IOException exception) {
            throw new FalhaRequisicaoWebException(String.format("Não foi possível obter a chave pública para verificar a assinatura digital do boletim de urna através da URL \"%s\".", url));
        }
    }
}
