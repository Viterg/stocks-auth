package ru.viterg.proselyte.stocks.auth.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.viterg.proselyte.stocks.auth.client.StocksClient;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StocksRestController {

    private final StocksClient stocksClient;

    @GetMapping("/")
    public Mono<List<String>> getCompanies(@RegisteredOAuth2AuthorizedClient("custom") OAuth2AuthorizedClient client) {
        return stocksClient.getCompanies();
        //        return webClient.get()
//                .uri("http://localhost:8088/api/v1/companies")
//                .attributes(oauth2AuthorizedClient(client))
//                .retrieve()
//                .bodyToMono(List.class)
//                .map(list -> (List<String>) list);
    }

    @GetMapping("/{ticker}")
    public Mono<BigDecimal> getStock(@RegisteredOAuth2AuthorizedClient("custom") OAuth2AuthorizedClient client,
            @PathVariable String ticker) {
        return stocksClient.getStock(ticker);
//        return webClient.get()
//                .uri("http://localhost:8088/api/v1/{ticker}/stock", ticker)
//                .attributes(oauth2AuthorizedClient(client))
//                .retrieve()
//                .bodyToMono(BigDecimal.class);
    }
}
