package ua.nure.st.patterns.labs.observer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class EventManagerConfig {

    @Bean
    @Scope("singleton")
    public ShopEventManager shopEventManager() {
        return new ShopEventManager();
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        return filter;
    }
}
