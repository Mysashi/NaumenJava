package ru.Ignatiev.NauJava.test.springTests.rest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomRequestsRestControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.reset();
        RestAssured.port = port;
    }

    @Test
    void shouldReturn200WhenAuthorsNotFoundAndUserUnauthorized() {
        given()
                .param("name", "John")
                .param("surname", "Doe")
                .when()
                .get("/api/custom/findByNameAndSurname")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturn404WhenAuthorsNotFoundAndUserAuthorized() {
        given()
                .auth()
                .form("admin", "admin")
                .when()
                .param("name", "Alex")
                .param("surname", "Summer")
                .when()
                .get("/api/custom/findByNameAndSurname")
                .then()
                .log().all()
                .statusCode(404);
    }
}