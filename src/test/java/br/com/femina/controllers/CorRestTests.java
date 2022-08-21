package br.com.femina.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CorRestTests {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8081/api";
    }

    @Test
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
    public void getCorCode200() {
        RestAssured.given()
            .when()
                .get("/cores")
            .then()
                .statusCode(200);
    }

    @Test
    public void getCorByIdCode200() {
        RestAssured.given()
            .when()
                .get("/cores/{id}",1)
            .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
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
