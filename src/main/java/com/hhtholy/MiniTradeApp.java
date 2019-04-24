package com.hhtholy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author hht
 * @create 2019-04-04 15:44
 */
@SpringBootApplication
public class MiniTradeApp  extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MiniTradeApp.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(MiniTradeApp.class);
    }
}
