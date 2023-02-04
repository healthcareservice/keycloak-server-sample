package com.example.keycloakserversample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Bean
  ReactiveClientRegistrationRepository getRegistration(
      @Value("${spring.security.oauth2.client.provider.sample.token-uri}") String token_uri,
      @Value("${spring.security.oauth2.client.registration.sample.client-id}") String client_id,
      @Value("${spring.security.oauth2.client.registration.sample.client-secret}") String client_secret
  ) {
    ClientRegistration registration = ClientRegistration
        .withRegistrationId("sample")
        .tokenUri(token_uri)
        .clientId(client_id)
        .clientSecret(client_secret)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .build();
    return new InMemoryReactiveClientRegistrationRepository(registration);
  }

  @Bean(name = "sampleWebClient")
  WebClient sampleWebClient(ReactiveClientRegistrationRepository clientRegistrations) {
    InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(
        clientRegistrations);
    AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
        clientRegistrations, clientService);
    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
        authorizedClientManager);
    oauth.setDefaultClientRegistrationId("sample");
    return WebClient.builder()
        .filter(oauth)
        .build();

  }

  @Bean(name = "notWorkingWebClient")
  WebClient notWorkingWebClient() {
    return WebClient.builder().build();

  }

}
