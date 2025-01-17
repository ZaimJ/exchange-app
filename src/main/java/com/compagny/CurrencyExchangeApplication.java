package com.compagny;

import com.compagny.conf.OpenAPIConfig;
import com.compagny.conf.RestTemplateConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableCaching
@Import({
        RestTemplateConfig.class,
        OpenAPIConfig.class})
public class CurrencyExchangeApplication {

    public static final void main(final String[] args) {
        SpringApplication.run(CurrencyExchangeApplication.class, args);
    }

}
