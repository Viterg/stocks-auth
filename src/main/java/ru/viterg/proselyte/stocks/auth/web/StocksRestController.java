package ru.viterg.proselyte.stocks.auth.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequiredArgsConstructor
public class StocksRestController {

    private final WebClient webClient;

    @GetMapping("/foos/{id}")
    public Mono<String> getFooResource(@RegisteredOAuth2AuthorizedClient("custom")
    OAuth2AuthorizedClient client, @PathVariable final long id){
        return webClient
                .get()
                .uri("http://localhost:8088/spring-security-oauth-resource/foos/{id}", id)
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class);
    }
}
