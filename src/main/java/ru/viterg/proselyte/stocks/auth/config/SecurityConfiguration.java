package ru.viterg.proselyte.stocks.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Value("${okta.oauth2.issuer}")
    private String issuer;
    @Value("${okta.oauth2.client-id}")
    private String clientId;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, ServerLogoutHandler logoutHandler) {
        http.authorizeExchange(exchange -> exchange
                        .pathMatchers("/", "/images/**", "/login").permitAll()
                        .anyExchange().authenticated())
                .oauth2Login(withDefaults())
                .logout(logout -> logout
                        .logoutHandler(logoutHandler))
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)));
        return http.build();
    }

    @Bean
    public ServerLogoutHandler logoutHandler() {
        return (webFilterExchange, authentication) -> {
            String baseUrl = "http://localhost:8084"; // TODO how to get it?
            URI redirectUri = URI.create("%sv2/logout?client_id=%s&returnTo=%s".formatted(issuer, clientId, baseUrl));
            ServerWebExchange serverWebExchange = webFilterExchange.getExchange();
            ServerHttpResponse response = serverWebExchange.getResponse();
            response.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
            response.getHeaders().setLocation(redirectUri);
            return response.setComplete();
        };
    }

    //    @Bean
    public UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
