package br.com.femina.controllers;

import br.com.femina.services.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public class ProdutoControllerTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

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
    public void getProdutos() {
        RestAssured.given()
                .when()
                .get("/produtos")
                .then()
                .statusCode(200);
    }

    @Test
    public void getProdutoById() {
        RestAssured.given()
                .when()
                .get("/produtos/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void getProdutoNotRegistered() {
        RestAssured.given()
                .when()
                .get("/produtos/2")
                .then()
                .statusCode(500);

    }

    @Test
    public void postProduto() throws Exception {
        RestAssured.given()
                .body("{\n" +

                        "            \"codigo\": \"00002\",\n" +
                        "            \"nome\": \"blusas\",\n" +
                        "            \"imagem\": null,\n" +
                        "            \"valor\": 40.990,\n" +
                        "            \"categoria\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"nome\": \"notebook\"\n" +
                        "            },\n" +
                        "            \"modelo\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"nome\": \"Joguer\"\n" +
                        "            },\n" +
                        "            \"fornecedor\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"name\": \"CamisetasAas\",\n" +
                        "                \"cnpj\": \"89752394753\",\n" +
                        "                \"telefone\": \"(41) 9472-7832\",\n" +
                        "                \"numero\": \"(41) 8734-9078\",\n" +
                        "                \"email\": \"camisetasAas@gmail.com\",\n" +
                        "                \"cep\": \"89872700\",\n" +
                        "                \"estado\": \"Sao Paulo\",\n" +
                        "                \"logradouro\": \"Rua Sao Matheus\",\n" +
                        "                \"cidade\": \"Chapeco\",\n" +
                        "                \"pais\": \"Brasil\"\n" +
                        "            },\n" +
                        "            \"marca\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"nome\": \"CamisetasAas\"\n" +
                        "            },\n" +
                        "            \"cor\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"nome\": \"vermelho\",\n" +
                        "                \"hexadecimal\": \"#ff0000\"\n" +
                        "            },\n" +
                        "            \"tamanho\": \"GG\",\n" +
                        "            \"descricao\": \"camiseta vermelha basica\",\n" +
                        "            \"destaque\": true\n" +
                        "        }")
                .contentType(ContentType.JSON)
                .when()
                .post("/produtos")
                .then()
                .statusCode(200);
    }

    @Test
    public void putProduto() {
        RestAssured.given()
                .body("{\n" +
                        "            \"id\": 2,\n" +
                        "            \"cadastrado\": \"2022-08-25T19:31:25.791918\",\n" +
                        "            \"atualizado\": null,\n" +
                        "            \"isActive\": true,\n" +
                        "            \"codigo\": \"00002\",\n" +
                        "            \"nome\": \"sapatos\",\n" +
                        "            \"imagem\": null,\n" +
                        "            \"valor\": 40.990,\n" +
                        "            \"categoria\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"nome\": \"notebook\"\n" +
                        "            },\n" +
                        "            \"modelo\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"nome\": \"Joguer\"\n" +
                        "            },\n" +
                        "            \"fornecedor\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"name\": \"CamisetasAas\",\n" +
                        "                \"cnpj\": \"89752394753\",\n" +
                        "                \"telefone\": \"(41) 9472-7832\",\n" +
                        "                \"numero\": \"(41) 8734-9078\",\n" +
                        "                \"email\": \"camisetasAas@gmail.com\",\n" +
                        "                \"cep\": \"89872700\",\n" +
                        "                \"estado\": \"Sao Paulo\",\n" +
                        "                \"logradouro\": \"Rua Sao Matheus\",\n" +
                        "                \"cidade\": \"Chapeco\",\n" +
                        "                \"pais\": \"Brasil\"\n" +
                        "            },\n" +
                        "            \"marca\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"nome\": \"CamisetasAas\"\n" +
                        "            },\n" +
                        "            \"cor\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"cadastrado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"atualizado\": \"2022-08-25T19:25:25.961598\",\n" +
                        "                \"isActive\": false,\n" +
                        "                \"nome\": \"vermelho\",\n" +
                        "                \"hexadecimal\": \"#ff0000\"\n" +
                        "            },\n" +
                        "            \"tamanho\": \"GG\",\n" +
                        "            \"descricao\": \"camiseta vermelha basica\",\n" +
                        "            \"destaque\": true\n" +
                        "        }")
                .contentType(ContentType.JSON)
                .when()
                .put("/produtos/2")
                .then()
                .statusCode(200);
    }

    @Test
    public void disableProduto() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .put("/produtos/disable/1")
                .then()
                .statusCode(200);
    }



}
