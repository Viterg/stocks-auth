package ru.viterg.proselyte.stocks.auth.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@ReactiveFeignClient(name = "stocksClient", url = "${application.client.sourceUrl}")
public interface StocksClient {

    @GetMapping("/companies")
    Mono<List<String>> getCompanies();

    @GetMapping("/{ticker}/stock")
    Mono<BigDecimal> getStock(@PathVariable String ticker);
}
