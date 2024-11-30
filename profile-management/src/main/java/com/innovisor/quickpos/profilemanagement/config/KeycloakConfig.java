package com.innovisor.quickpos.profilemanagement.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class KeycloakConfig {

    @Value("${app.keycloak.admin.clientId}")
    private String clientId;

    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;

    @Value("${app.keycloak.admin.realm}")
    private String realm;

    @Value("${app.keycloak.admin.serverUrl}")
    private String serverUrl;

    private Keycloak keycloak;

    @Bean
    public Keycloak keycloak() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
        return keycloak;
    }

    public UsersResource getUserResource() {
        return keycloak.realm(realm).users();
    }

    public String getClientSecret() {
        RealmResource realmResource = keycloak.realm(realm);
        Optional<ClientRepresentation> clientOpt = realmResource.clients().findByClientId(clientId).stream().findFirst();

        if (clientOpt.isPresent()) {
            ClientRepresentation client = clientOpt.get();
            ClientResource clientResource = realmResource.clients().get(client.getId());
            return clientResource.getSecret().getValue();
        } else {
            throw new RuntimeException("Client not found: " + clientId);
        }
    }

    public String getClientName() {
        RealmResource realmResource = keycloak.realm(realm);
        Optional<ClientRepresentation> clientOpt = realmResource.clients().findByClientId(clientId).stream().findFirst();

        if (clientOpt.isPresent()) {
            ClientRepresentation client = clientOpt.get();
            return client.getName();  // Retrieves and returns the client name
        } else {
            throw new RuntimeException("Client not found: " + clientId);
        }
    }


}
