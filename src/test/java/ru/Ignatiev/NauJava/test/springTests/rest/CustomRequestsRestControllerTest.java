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
    void shouldReturn404WhenAuthorsNotFound() {
        given()
                .log().all()
                .param("name", "John")
                .param("surname", "Doe")
                .when()
                .post("/api/custom/findByNameAndSurname")
                .then()
                .statusCode(404);
    }
}