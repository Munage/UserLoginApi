# User-login-api
## An Example Spring Boot Application for Securing a REST API with JWT-auth tokens

This application is an example project on how to use JWT auth tokens to secure a REST-api. 

## Main building blocks
 * Spring Boot 2.1.2.RELEASE go to http://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/htmlsingle/ to learn more about spring boot
 * JSON Web Token go to https://jwt.io/ to decode your generated token and learn more
 * (Coming soon) H2 Database Engine - used for rapid prototyping and development, but not suitable for production at least in most cases. Go to www.h2database.com to learn more

## Requirements
* Java (1.8)
* Maven
* (Optional) Postman

## To run the application
Build using maven goal: `mvn clean package` and execute the resulting artifact as follows `java -jar user-auth-0.0.1-SNAPSHOT.jar`

## To test the application
The supplied Postman collection contains sample calls to all the url endpoints and can be used for testing purposes.

