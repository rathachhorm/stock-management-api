package com.innovisor.quickpos.profilemanagement.config;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        var authorities = Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRole(jwt).stream()
        );
        return null;
    }

    private Collection<? extends GrantedAuthority> extractResourceRole(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if(jwt.getClaim("resource_access")){
            return Set.of();
        }
        resourceAccess = jwt.getClaims();

        if(resourceAccess.get("profile-management") == null){
            return Set.of();
        }
        return null;
    }
}
