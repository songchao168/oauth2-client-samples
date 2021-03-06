OAuth2 Spring TodoList Application Client
=================================

![oauth2-client-play-todolist] (https://github.com/tcompiegne/oauth2-client-samples/raw/master/springmvc-todolist/site/oauth2_spring_client_homepage.png)


This is a simple Web Application Client that provides a Todo List Manager fetching Resource Provider protected by OAuth 2. The application is based on Spring Framework (Spring Boot with Spring MVC and Spring Rest Template). 

You can check the [parent project](https://github.com/tcompiegne/oauth2-client-samples) to get more details about the other parts of this project (OAuth2 Authorization Server and OAuth Resource Providers).

## Client side
* [Twitter bootstrap](http://getbootstrap.com/)
* [HTML5](http://www.w3.org/TR/html5/) and [CSS3](http://www.w3schools.com/css/css3_intro.asp)

## Server side
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html)
* [Spring Rest Template](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)
* [Thymeleaf](http://www.thymeleaf.org/)

## Run the application
```
$ git clone https://github.com/tcompiegne/oauth2-client-samples
$ cd oauth2-client-samples/springmvc-todolist
$ mvn spring-boot:run
...
(app starts and listen on port 9000)

browse the following URL: http://localhost:9000
```
