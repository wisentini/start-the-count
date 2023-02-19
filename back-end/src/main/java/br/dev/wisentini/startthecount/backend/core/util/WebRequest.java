package br.dev.wisentini.startthecount.backend.core.util;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.stream.Collectors;

public class WebRequest {

    private static final String GET_METHOD_NAME = "GET";

    public static JsonObject getJSON(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod(GET_METHOD_NAME);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.lines().collect(Collectors.joining());

        connection.disconnect();

        JsonElement jsonElement = JsonParser.parseString(response);

        return jsonElement.getAsJsonObject();
    }

    public static byte[] getBytes(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod(GET_METHOD_NAME);

        byte[] bytes = connection.getInputStream().readAllBytes();

        connection.disconnect();

        return bytes;
    }
}
