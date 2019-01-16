# User-login-api
## An Example Spring Boot Application for Securing a REST API with JWT-auth tokens

This application is an example project on how to use JWT auth tokens to secure a REST-api. 

## Main building blocks
 * Spring Boot 2.1.2.RELEASE go to http://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/htmlsingle/ to learn more about spring boot
 * JSON Web Token go to https://jwt.io/ to decode your generated token and learn more
 * H2 Database Engine - used for rapid prototyping and development, but not suitable for production at least in most cases. Go to www.h2database.com to learn more

## Requirements
* Java (1.8)
* Maven
* (Optional) Postman

## To run the application
Build using maven goal: `mvn clean package` and execute the resulting artifact as follows `java -jar user-auth-0.0.1-SNAPSHOT.jar`

## To test the application
The supplied Postman collection contains sample calls to all the url endpoints and can be used for testing purposes.

##1. Register new user
* [PUT] /api/users

The application comes with 2 users bootstrapped to the database, calling the register new user endpoint will produce 
a response similar to the following:
 
    {
      "id": 3,
      "token": "eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSs8uLU4tSizIVNJRykwsUbIyNDUxNzMwMjMx1FFKrSiAC1gYAQVAavMSc1OBGr3yM_KUagHKJWkqRwAAAA.8gqyjqJduU79T0IExczeyOVqlJemsBocbu7fcDdIlIo"
    }

User names are required to be unique, they are however, not case sensitive. 
Registering a user also does a log in for that user and the postman scripts will automatically insert the latest auth 
token into the authorization header of future requests. 


##2. Login user
* [POST] /api/users

The login request comes preconfigured for one of the preconfigured users in the database. The login response is similar to the following: 

     {
         "id": 2,
         "token": "eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSs8uLU4tSizIVNJRykwsUbIyNDUxNzMwNjAw01FKrSiACxhaAAVAavMSc1OBGhNTcjPzlGoBoT68n0gAAAA.GO5GP8VemUX0RkoWelXK-zBw-iE8UMYxLkivSJK9zCE"
     }
     
##3. List registered users
* [GET] /api/users

This endpoint does not require an auth token, and will list all users that are registered in the database:

    
    {
        "users": [
            {
                "username": "john",
                "phone": "083456456"
            },
            {
                "username": "admin",
                "phone": "067567677"
            },
            {
                "username": "John",
                "phone": "0831234123"
            }
        ]
    }
    
##4. Logout
* [POST] /api/logout/{id}

The logout api terminates the user session stored in the database. However, due to the stateless nature of jwts tokens used
in the authentication process, the the token itself remains valid until it expires. It is thus recommended that the client
application removes any references to the auth token on logout. 
              
    {
        "result": "Success - Logged out successfully"
    }

Currently only the auth token is used in the logout endpoint. The user id has only remained in order to maintain api consistency.
The possibility exists to expand the session validation in future to include the user ID along with the auth token.  

##5. Count valid sessions
* [GET] /api/users/authenticated

The authenticated endpoint requires an authorisation token, upon validating, checks the session table for valid user sessions 
and return the total number of valid user sessions. 

    {
        "active_sessions": 3
    }
