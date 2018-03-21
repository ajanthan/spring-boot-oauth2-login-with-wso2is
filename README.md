# spring-boot-oauth2-login-with-wso2is
This a Spring Boot example with spring security OAuth2 login using WSO2 Identity server

## Step 1

Visit [https://start.spring.io](https://start.spring.com) and generate skeleton project by adding web,Security and Thymeleaf as dependencies.

![start.spring.io](images/start-spring-io.png?raw=true "Generate Spring Boot Project")

## Step 2

Add following dependencies to pom.xml

```xml
		<!--Additional Dependencies-->
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-oauth2-client</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-oauth2-jose</artifactId>
		</dependency>
		<dependency>
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity4</artifactId>
		</dependency>
        
```

## Step 3

Add controller class to serve traffic for index.html page.

```java
package com.github.ajanthan.spring.security.oauth2loginsample.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String getUserName(Model model, OAuth2AuthenticationToken token) {
        model.addAttribute("userName", token.getPrincipal().getName());
        return "index";
    }
}

```
Add the template with holder for displaying username.

```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>OAuth2 Login </title>
</head>
<body>
<div style="float: right">
    <div style="float:left">
        <span style="font-weight:bold">User: <span th:text=${userName}></span> </span>
    </div>
    <div style="float:none">&nbsp;</div>
    <div style="float:right">
        <form th:action="@{/logout}" method="post">
            <input type="submit" value="Logout"/>
        </form>
    </div>
</div>
<h1>OAuth 2.0 Login with Spring Security</h1>
<div>
    You are successfully logged in as <span style="font-weight:bold" th:text="${userName}"></span>
</div>
</body>
</html>

```

## Step 4

Add OAuth2LoginConfig class to configure InMemoryClientRegistrationRepository with detail specific to WSO2 and the registered OAuth2 client that is going to be used for login.

```java

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

```
This implemetation is going to read certain parameters from the application.properties. This is a sample application properties.

```properties
spring.thymeleaf.cache=false
spring.security.oauth2.client.registration.wso2.client-id=ilv61uSGnTE8Raulnpjo7jnydf8a #Client Id of registered service provider in WSO2 identity server
spring.security.oauth2.client.registration.wso2.client-secret=9KAYRlx3WFnGLh_mmIpW0l7oJmka #Client secret of registered service provider in WSO2 identity server
spring.security.oauth2.client.provider.wso2.authorization-uri=http://idp.example.com:9763/oauth2/authorize
spring.security.oauth2.client.provider.wso2.token-uri=http://idp.example.com:9763/oauth2/token
spring.security.oauth2.client.provider.wso2.user-info-uri=http://idp.example.com:9763/oauth2/userinfo
spring.security.oauth2.client.provider.wso2.jwk-set-uri=http://idp.example.com:9763/oauth2/jwks
```
