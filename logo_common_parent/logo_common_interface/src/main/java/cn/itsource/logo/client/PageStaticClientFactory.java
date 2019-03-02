package cn.itsource.logo.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PageStaticClientFactory implements FallbackFactory<PageStaticClient> {

    public PageStaticClient create(Throwable throwable) {
        return new PageStaticClient() {
            public void getPageStatic(Map<String, Object> map) {

            }


        };
    }
}
