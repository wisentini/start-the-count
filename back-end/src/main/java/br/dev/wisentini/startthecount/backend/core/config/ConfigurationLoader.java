package br.dev.wisentini.startthecount.backend.core.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationLoader {

    public static Configuration getConfiguration(String filename) {
        try (InputStream inputStream = ConfigurationLoader.class.getClassLoader().getResourceAsStream(filename)) {
            return new Yaml(new Constructor(Configuration.class)).load(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException("Não foi possível carregar o arquivo de configuração da aplicação.");
        }
    }
}
