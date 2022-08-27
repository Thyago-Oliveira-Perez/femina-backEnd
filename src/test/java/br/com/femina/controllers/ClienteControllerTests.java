package br.com.femina.controllers;


import br.com.femina.entities.enums.Sexo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteControllerTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Autowired
    private ClienteService clienteService;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Get client by id")
    public void getByIdClient_status200() {

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", "1")
                .when()
                .get("/clientes/{id}")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
        assertNotNull(response.contentType());
    }

    @Test
    @DisplayName("get by non-existent id")
    public void getByIdNotRegistered_status400() {

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", "20")
                .when()
                .get("/clientes/{id}")
                .then()
                .extract().response();

        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("insert one client")
    public void postClient_status200() {
        Cliente client = new Cliente();

        client.setCadastrado(LocalDateTime.now());
        client.setAtualizado(LocalDateTime.now());
        client.setIsActive(true);
        client.setNome("teste01");
        client.setLogin("testeLogin00");
        client.setSenha("1234");
        client.setSexo(Sexo.MASCULINO);
        client.setDataNascimento(new Date());
        client.setCpf("12345678909-38");
        client.setEmail("emailDemail@gmail.com");
        client.setTelefone("87937129922");
        client.setPais("Brasil");
        client.setEstado("Parana");
        client.setCidade("Foz do Iguaçu");
        client.setLogradouro("Vila Yolanda");
        client.setNumero("48");
        client.setCep("00000-000");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(asJsonString(client))
                .when()
                .post("/clientes")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("edit client")
    public void editClient_status200() {
        Cliente client = new Cliente();

        client.setId((long) 1);
        client.setCadastrado(LocalDateTime.now());
        client.setAtualizado(LocalDateTime.now());
        client.setIsActive(true);
        client.setNome("teste01");
        client.setLogin("testeLogin12");
        client.setSenha("1234");
        client.setSexo(Sexo.MASCULINO);
        client.setDataNascimento(new Date());
        client.setCpf("12276438912-38");
        client.setEmail("teste@gmail.com");
        client.setTelefone("87937129922");
        client.setPais("Brasil");
        client.setEstado("Parana");
        client.setCidade("Foz do Iguaçu");
        client.setLogradouro("Vila Yolanda");
        client.setNumero("48");
        client.setCep("00000-000");

        Response response = given()
                            .contentType(ContentType.JSON)
                            .header("Content-type", "application/json")
                            .pathParam("id", "1")
                            .body(asJsonString(client))
                            .when()
                            .put("/clientes/{id}")
                            .then()
                            .extract().response();

        assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("disable client")
    public void disableClient_status200(){
        Response response = given()
                .header("Content-type", "application/json")
                .pathParam("id", "1")
                .when()
                .put("/clientes/disable/{id}")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
    }
}
