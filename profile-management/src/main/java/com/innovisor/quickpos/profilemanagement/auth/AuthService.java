package com.innovisor.quickpos.profilemanagement.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovisor.quickpos.profilemanagement.config.KeycloakConfig;
import com.innovisor.quickpos.profilemanagement.profile.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final KeycloakConfig keycloak;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TokenResponse login(LoginRequest loginRequest){
        return executeTokenRequest(createAuthParams(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    public String getToken(AuthRequest authRequest) {
        return executeTokenRequest(createAuthParams(authRequest.getEmail(), authRequest.getPassword())).getAccessToken();
    }

    public String refreshToken(AuthRequest authRequest) {
        System.out.println(executeTokenRequest(createAuthParams(authRequest.getEmail(), authRequest.getPassword())).getRefreshToken());
        return executeTokenRequest(createAuthParams(authRequest.getEmail(), authRequest.getPassword())).getRefreshToken();
    }



    private TokenResponse executeTokenRequest(Map<String, String> params) {
        HttpPost post = new HttpPost(URI.create("https://keycloack.quicksoftteam.com/realms/stock-management/protocol/openid-connect/token"));
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");

        try {
            post.setEntity(new UrlEncodedFormEntity(convertToNameValuePair(params)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode URL parameters", e);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            String content = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(content);
            return objectMapper.readValue(jsonObject.toString(), TokenResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve token", e);
        }
    }

    private List<NameValuePair> convertToNameValuePair(Map<String, String> params) {
        List<NameValuePair> urlParameters = new ArrayList<>();
        params.forEach((key, value) -> urlParameters.add(new BasicNameValuePair(key, value)));
        return urlParameters;
    }

    private Map<String, String> createAuthParams(String email, String password) {
        return Map.of(
                "client_id", keycloak.getClientName(),
                "username", email,
                "password", password,
                "grant_type", "password",
                "client_secret", keycloak.getClientSecret()
        );
    }

}
