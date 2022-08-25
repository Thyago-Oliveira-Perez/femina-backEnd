package br.com.femina.controllers;

import br.com.femina.entities.Cargo;
import br.com.femina.entities.Funcionario;
import br.com.femina.entities.Sexo;
import br.com.femina.services.FuncionarioService;
import br.com.femina.services.ProdutoService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bytebuddy.dynamic.DynamicType;
import org.hibernate.type.LocalDateType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FuncionarioControllerTests.class)
public class FuncionarioControllerTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

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
    public void getFuncionarios() {
        RestAssured.given()
                .when()
                .get("/funcionarios")
                .then()
                .statusCode(200);
    }

    @Test
    public void getFuncionarioById() {
        RestAssured.given()
                .when()
                .get("/funcionarios/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void getByIdNotRegistered() {
        RestAssured.given()
                .when()
                .get("/funcionarios/10")
                .then()
                .statusCode(500);
    }

    @Test
    public void postFuncionarios() throws Exception {
        RestAssured.given()
                .body("{\n" +
                        "    \"nome\": \"teste\",\n" +
                        "    \"login\": \"teste\",\n" +
                        "    \"senha\": \"123\",\n" +
                        "    \"cpf\": \"000.000.000-00\",\n" +
                        "    \"sexo\": \"MASCULINO\",\n" +
                        "    \"dataNascimento\": \"2022-08-24T19:24:28.948358\",\n" +
                        "    \"email\": \"vitor@vitor.com\",\n" +
                        "    \"telefone\": \"00000000000\",\n" +
                        "    \"pais\": \"br\",\n" +
                        "    \"estado\": \"pr\",\n" +
                        "    \"cidade\": \"teste\",\n" +
                        "    \"logradouro\": \"teste\",\n" +
                        "    \"numero\": \"123\",\n" +
                        "    \"cep\": \"00000-000\",\n" +
                        "    \"salario\": 100.000,\n" +
                        "    \"cargo\": \"FUNCIONARIO\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/funcionarios")
                .then()
                .statusCode(200);
    }

    @Test
    public void putFuncionario() {
        RestAssured.given()
                .body("{\n" +
                        "            \"id\": 9,\n" +
                        "            \"cadastrado\": \"2022-08-24T19:52:16.517589\",\n" +
                        "            \"atualizado\": null,\n" +
                        "            \"isActive\": true,\n" +
                        "            \"nome\": \"teste put\",\n" +
                        "            \"login\": \"teste\",\n" +
                        "            \"senha\": \"123\",\n" +
                        "            \"cpf\": \"000.000.000-00\",\n" +
                        "            \"sexo\": \"MASCULINO\",\n" +
                        "            \"dataNascimento\": \"2022-08-24T19:24:28.948+00:00\",\n" +
                        "            \"email\": \"vitor@vitor.com\",\n" +
                        "            \"telefone\": \"00000000000\",\n" +
                        "            \"pais\": \"br\",\n" +
                        "            \"estado\": \"pr\",\n" +
                        "            \"cidade\": \"teste\",\n" +
                        "            \"logradouro\": \"teste\",\n" +
                        "            \"numero\": \"123\",\n" +
                        "            \"cep\": \"00000-000\",\n" +
                        "            \"salario\": 100.00,\n" +
                        "            \"cargo\": \"FUNCIONARIO\"\n" +
                        "        }")
                .contentType(ContentType.JSON)
                .when()
                .put("/funcionarios/9")
                .then()
                .statusCode(200);
    }

    @Test
    public void disableFuncionario() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .put("/funcionarios/disable/3")
                .then()
                .statusCode(200);
    }

}
