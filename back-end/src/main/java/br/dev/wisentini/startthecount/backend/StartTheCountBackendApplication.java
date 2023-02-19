package br.dev.wisentini.startthecount.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StartTheCountBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartTheCountBackendApplication.class, args);
    }
}
