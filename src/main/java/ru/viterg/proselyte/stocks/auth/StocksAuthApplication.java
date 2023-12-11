package ru.viterg.proselyte.stocks.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
public class StocksAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksAuthApplication.class, args);
    }
}
