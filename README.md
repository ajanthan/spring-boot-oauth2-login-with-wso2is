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
