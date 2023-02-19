package br.dev.wisentini.startthecount.backend.core.service;

import br.dev.wisentini.startthecount.backend.core.exception.FalhaRequisicaoWebException;
import br.dev.wisentini.startthecount.backend.core.model.complemento.BoletimUrna;
import br.dev.wisentini.startthecount.backend.core.util.WebRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;

public class ComplementoBoletimUrnaCacheService extends CacheService<BoletimUrna> {

    private static final Gson gson = new GsonBuilder().create();

    public BoletimUrna get(String url) {
        try {
            if (redis.exists(url)) {
                return gson.fromJson(redis.get(url), BoletimUrna.class);
            }

            JsonObject jsonObject = WebRequest.getJSON(url);

            BoletimUrna complementoBoletimUrna = gson.fromJson(jsonObject, BoletimUrna.class);

            redis.set(url, gson.toJson(complementoBoletimUrna));

            return complementoBoletimUrna;
        } catch (IOException exception) {
            throw new FalhaRequisicaoWebException(String.format("Não foi possível obter os dados complementares do boletim de urna através da URL \"%s\".", url));
        }
    }
}
