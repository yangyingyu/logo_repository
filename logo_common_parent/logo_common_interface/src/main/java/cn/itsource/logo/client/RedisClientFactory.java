package cn.itsource.logo.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisClientFactory implements FallbackFactory<RedisClient> {
    public RedisClient create(Throwable throwable) {
        return new RedisClient() {
            public void set(String key, String value) {

            }

            public String get(String key) {
                return null;
            }
        };
    }
}
