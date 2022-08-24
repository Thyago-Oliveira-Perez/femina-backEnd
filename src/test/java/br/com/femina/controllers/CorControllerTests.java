package br.com.femina.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.annotation.Order;

import static org.hamcrest.Matchers.equalTo;

public class CorControllerTests {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8081/api";
    }

    @Test
    @Order(1)
    public void insertCorCode200() {
        RestAssured.given()
            .body("{\"nome\": \"Teste\", \"hexadecimal\": \"#000000\"}")
            .contentType(ContentType.JSON)
                .when()
                    .post("/cores")
                .then()
                    .statusCode(200);
    }

    @Test
    @Order(2)
    public void getCorCode200() {
        RestAssured.given()
            .when()
                .get("/cores")
            .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    public void getCorByIdCode200() {
        RestAssured.given()
            .when()
                .get("/cores/{id}",1)
            .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    @Order(4)
    public void disableCorCode200() {
        RestAssured.given()
            .body("{\"nome\": \"teste\", \"hexadecimal\": \"#000000\"}")
            .contentType(ContentType.JSON)
                .when()
                    .put("/cores/disable/1")
                .then()
                    .statusCode(200);
    }

}
