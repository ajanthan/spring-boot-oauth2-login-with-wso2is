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
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>OAuth2 Login </title>
</head>
<body>
<div style="float: right">
    <div style="float:left">
        <span style="font-weight:bold">User: ${userName} </span>
    </div>
    <div style="float:none">&nbsp;</div>
    <div style="float:right">
        <form action="#" action="@{/logout}" method="post">
            <input type="submit" value="Logout"/>
        </form>
    </div>
</div>
<h1>OAuth 2.0 Login with Spring Security</h1>
<div>
    You are successfully logged in <span style="font-weight:bold" text="${userName}"></span>
</div>
</body>
</html>

```
