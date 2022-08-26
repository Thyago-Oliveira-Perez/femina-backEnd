package br.com.femina.controllers;

import br.com.femina.entities.Favoritos;
import br.com.femina.entities.Marca;
import br.com.femina.services.FavoritosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoritosControllerTests {

    @MockBean
    private FavoritosService favoritosService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void insertFavoritosCode200() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/favoritos?idProduto=1&idCliente=1")
                .then()
                .statusCode(200);
    }

    @Test
    public void getFavoritosCode200() {
        given()
                .when()
                .get("/favoritos")
                .then()
                .statusCode(200);
    }

    @Test
    public void getFavoritosByIdCode200() {
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", "5")
                .when()
                .get("/favoritos/{id}")
                .then()
                .extract().response();
        assertEquals (200, response.statusCode());
    }

    @Test
    public void disableFavoritos(){
        Response response = given()
                .pathParam("id", "5")
                .when()
                .put("//favoritos/disable/{id}")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
    }

}
