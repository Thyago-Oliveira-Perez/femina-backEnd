package br.com.femina.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ModeloRestTests {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8081/api";
    }

    @Test
    public void insertModeloCode200() {
        RestAssured.given()
            .body("{\"nome\": \"Teste\"}")
            .contentType(ContentType.JSON)
                .when()
                    .post("/modelos")
                .then()
                    .statusCode(200);
    }

    @Test
    public void getModelosCode200() {
        RestAssured.given()
            .when()
                .get("/modelos")
            .then()
                .statusCode(200);
    }

    @Test
    public void getModeloByIdCode200() {
        RestAssured.given()
            .when()
                .get("/modelos/{id}",1)
            .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void disableModelosCode200() {
        RestAssured.given()
            .body("{\"nome\": \"teste\"}")
            .contentType(ContentType.JSON)
                .when()
                    .put("/modelos/disable/1")
                .then()
                    .statusCode(200);
    }

}
