package com.github.ajanthan.spring.security.oauth2loginsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

@Configuration
public class OAuth2LoginConfig {
    private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.wso2.";
    private static String PROVIDER_PROPERTY_KEY = "spring.security.oauth2.client.provider.wso2.";
    @Autowired
    private Environment env;

    @Bean
    public ClientRegistrationRepository
    clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.WSO2ClientRegistration());
    }

    private ClientRegistration WSO2ClientRegistration() {
        return ClientRegistration.withRegistrationId("wso2")
                .clientId(env.getProperty(CLIENT_PROPERTY_KEY + "client-id"))
                .clientSecret(env.getProperty(CLIENT_PROPERTY_KEY + "client-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("openid", "profile", "email", "address", "phone")
                .authorizationUri(env.getProperty(PROVIDER_PROPERTY_KEY + "authorization-uri"))
                .tokenUri(env.getProperty(PROVIDER_PROPERTY_KEY + "token-uri"))
                .userInfoUri(env.getProperty(PROVIDER_PROPERTY_KEY + "user-info-uri"))
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri(env.getProperty(PROVIDER_PROPERTY_KEY + "jwk-set-uri"))
                .clientName("WSO2")
                .build();
    }
}
