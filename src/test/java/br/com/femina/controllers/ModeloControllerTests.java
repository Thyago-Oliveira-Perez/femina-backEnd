package br.com.femina.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ModeloController.class)
public class ModeloControllerTests {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8081/api";
    }

    @Test
    @Order(1)
    public void insertModeloCode200() {
        RestAssured.given()
                .body("{\"nome\": \"Teste\", \"isActive\": "+true+" }")
                .contentType(ContentType.JSON)
                .when()
                .post("/modelos")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void getModelosCode200() {
        RestAssured.given()
                .when()
                .get("/modelos")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    public void getModeloByIdCode200() {
        RestAssured.given()
                .when()
                .get("/modelos/{id}",1)
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    @Order(4)
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