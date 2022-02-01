# Cinema.
## Demo
## Description
### This is code describes a backend for a small cinema. 
It contains:
A backend for administrating a cinema.
It allows viewing available movies, post reviews, view available movie sessions for date,
consult movie description. 

## Concept Model Description
The cinema plays movies, which are played in movie sessions.
For example: Fast and Furious is a movie played in the cinema - Movie.
This movie is played at 14:00 on 01.01.2024 in blue room for a price 10 - Movie Session.
Anyone can post a rating for a Movie.
Anyone can consult a movie description.

## Implementation:
1. Java 11.
2. Spring-Boot 2.6
3. Spring JPA with Hibernate 2.6
4. Lombok 1.18
5. H2 Database 1.6
6. Maven.
7. Swagger.
8. Mockito.
9. Junit

## How to run
You can build and run the jar directly or you can use your favorite IDE.
### Provide as environment variables:
1. TOKEN - which will be used to communicate with endpoint used for administrative tasks. It is part of env variables, and will be used in Bearer header for administrative endpoint (you can assign the value you like as token). 
2. IMDB_API_KEY - which is used to communicate with external API for movies description. You can get this API_KEY going to  [OMDB](http://www.omdbapi.com/) .


### Endpoints documentation is available on http://{host}}:8080/swagger-ui/index.html
### H2 console available during runtime http://{host}:8080/h2-console/

## Considerations
- It's a demo made in particular timeframe, scope, and done in spare time. 
- Some concepts are applied only to certain classes to show the knowledge of the concept. 
- The administrative endpoint secured with a token with Bearer header. Since there were no specifications, this approach was taken to fulfill the requirement.
- The validation classes for web input and data clean up are only applied to one method to show the concept.
- Testing is incomplete as per coverage, and it contains only integration testing for each layer and E2E test. This complies with requirements.

## Improvements and clarifications
- It started as DDD project, but the whole model was anemic. The business domain was just a POJO, since there is no business logic, it s more of a CRUD.
That's why the domain package has been deleted, and the communication relies on DTO. 
- The administrative endpoint secured with a token with Bearer header.
This can be done in other several ways (e.g: Basic Login with Spring Security, 
  Login using user and password storing users data with password hash and salt, etc.).
- Logging should be improved.
- Custom exceptions for better error descriptions.
- Improve testing coverage, add integration test with no mock for external API and run it based on profile, when need to check ral communication. For everyday task, mocks can be used.


  


