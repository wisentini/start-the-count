package br.dev.wisentini.startthecount.backend.core.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedisConfiguration {

    private String host;

    private Integer port;
}
