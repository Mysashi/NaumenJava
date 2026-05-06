package ru.Ignatiev.NauJava.test.springTests.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomRequestsRestControllerTest {



    @Test
    void shouldReturn401WhenAuthorsNotFoundAndUserUnauthorized() {
        given()
                .param("name", "John")
                .param("surname", "Doe")
                .when()
                .get("/api/custom/author/findByNameAndSurname")
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