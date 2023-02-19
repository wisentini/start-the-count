package br.dev.wisentini.startthecount.backend.core.util;

import br.dev.wisentini.startthecount.backend.core.config.ConfigurationLoader;
import br.dev.wisentini.startthecount.backend.core.config.RedisConfiguration;

import redis.clients.jedis.JedisPooled;

public class RedisConnection {

    private final JedisPooled jedis;

    private RedisConnection() {
        RedisConfiguration redisConfiguration = ConfigurationLoader.getConfiguration("core.yaml").getRedis();

        this.jedis = new JedisPooled(redisConfiguration.getHost(), redisConfiguration.getPort());
    }

    private static class RedisConnectionHolder {

        private static final RedisConnection redisConnection = new RedisConnection();
    }

    public static RedisConnection getInstance() {
        return RedisConnectionHolder.redisConnection;
    }

    public void set(String key, String value) {
        this.jedis.set(key, value);
    }

    public String get(String key) {
        return this.jedis.get(key);
    }

    public boolean exists(String key) {
        return this.jedis.exists(key);
    }
}
