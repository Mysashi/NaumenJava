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
    void shouldReturn401WhenAuthorsNotFoundAndUserUnauthorized() {
        given()
                .param("name", "John")
                .param("surname", "Doe")
                .when()
                .get("/api/custom/findByNameAndSurname")
                .then()
                .statusCode(401);
    }

    @Test
    void shouldReturn404WhenAuthorsNotFoundAndUserAuthorized() {
        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .param("name", "Alex")
                .param("surname", "Summer")
                .get("/api/custom/author/findByNameAndSurname")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void testGetReportUnauthorized() {
        given()
                .when()
                .get("/report/26")
                .then()
                .statusCode(401);
    }
}