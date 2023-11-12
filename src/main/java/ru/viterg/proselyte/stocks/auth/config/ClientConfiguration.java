package ru.viterg.proselyte.stocks.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

    @Bean
    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrationRepo,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepo) {
        var filter = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepo, authorizedClientRepo);
        return WebClient.builder()
                .filter(filter)
                .build();
    }

}
