package com.hhtholy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hht
 * @create 2019-04-04 15:44
 */
@SpringBootApplication
@EnableCaching
public class MiniTradeApp  extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MiniTradeApp.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(MiniTradeApp.class);
    }
}
