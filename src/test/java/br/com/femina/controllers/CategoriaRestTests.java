package br.com.femina.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CategoriaRestTests {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8081/api";
    }

    @Test
    public void insertCategoriaCode200() {
        RestAssured.given()
            .body("{\"nome\": \"Teste\"}")
            .contentType(ContentType.JSON)
                .when()
                    .post("/categorias")
                .then()
                    .statusCode(200);
    }

    @Test
    public void getCategoriasCode200() {
        RestAssured.given()
            .when()
                .get("/categorias")
            .then()
                .statusCode(200);
    }

    @Test
    public void getCategoriaByIdCode200() {
        RestAssured.given()
            .when()
                .get("/categorias/{id}",1)
            .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void disableCategoriasCode200() {
        RestAssured.given()
            .body("{\"nome\": \"teste\"}")
            .contentType(ContentType.JSON)
                .when()
                    .put("/categorias/disable/1")
                .then()
                    .statusCode(200);
    }
}
