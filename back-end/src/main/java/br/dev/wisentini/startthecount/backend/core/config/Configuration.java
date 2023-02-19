package br.dev.wisentini.startthecount.backend.core.config;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {

    private RedisConfiguration redis;

    private URLsConfiguration urls;
}
