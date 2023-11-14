package ru.viterg.proselyte.stocks.auth.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StocksRestController {

    private final WebClient webClient;

    @GetMapping("/")
    public Mono<List<String>> getCompanies(@RegisteredOAuth2AuthorizedClient("custom") OAuth2AuthorizedClient client) {
        return webClient.get()
                .uri("http://localhost:8088/api/v1/companies")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(List.class)
                .map(list -> (List<String>) list);
    }

    @GetMapping("/{ticker}")
    public Mono<BigDecimal> getStock(@RegisteredOAuth2AuthorizedClient("custom") OAuth2AuthorizedClient client,
            @PathVariable String ticker) {
        return webClient.get()
                .uri("http://localhost:8088/api/v1/{ticker}/stock", ticker)
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(BigDecimal.class);
    }
}
