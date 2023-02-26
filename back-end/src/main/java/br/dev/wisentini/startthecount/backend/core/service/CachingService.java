package br.dev.wisentini.startthecount.backend.core.service;

import br.dev.wisentini.startthecount.backend.core.util.RedisConnection;

public abstract class CachingService<T> {

    protected static final RedisConnection redis = RedisConnection.getInstance();

    public abstract T get(String key);
}
