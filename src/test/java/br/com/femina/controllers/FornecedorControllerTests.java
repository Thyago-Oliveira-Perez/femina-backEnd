package br.com.femina.controllers;

import br.com.femina.entities.Categorias;
import br.com.femina.entities.Fornecedor;
import br.com.femina.services.FornecedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlTemplate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FornecedorController.class)
public class FornecedorControllerTests {

        @BeforeAll
        public static void setup() {
            RestAssured.baseURI = "http://localhost:8080/api";
        }
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private FornecedorService fornecedorService;

    // insert get getById Put diseable
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
        public void getFornecedor(){
            RestAssured.given()
                    .when()
                    .get("/fornecedores")
                    .then()
                    .statusCode(200);
        }

        @Test
        public void getFornecedorById() {
            RestAssured.given()
                    .when()
                    .get("/fornecedores/1")
                    .then()
                    .statusCode(200);
        }

        @Test
        public void getByIdNotRegistered() {
            RestAssured.given()
                    .when()
                    .get("/fornecedores/10")
                    .then()
                    .statusCode(500);
        }
        @Test
        public void postFornecedores() throws Exception {
            RestAssured.given()
                    .body("{\n" +
                            "    \"nome\": \"teste\",\n" +
                            "    \"cnpj\": \"00.000.000/0000-00\",\n" +
                            "    \"telefone\": \"00000000000\",\n" +
                            "    \"numero\": \"00000000000\",\n" +
                            "    \"email\": \"day@gmail.com\",\n" +
                            "    \"estado\": \"pr\",\n" +
                            "    \"cidade\": \"foz\",\n" +
                            "    \"logradouro\": \"santa\",\n" +
                            "    \"numero\": \"123\",\n" +
                            "    \"cep\": \"00000-000\",\n" +
                            "    \"pais\": \"br\",\n" +
                            "}")
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/fornecedores")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void putFuncionario() {
            RestAssured.given()
                    .body("{\n" +
                            "    \"nome\": \"teste\",\n" +
                            "    \"cnpj\": \"00.000.000/0000-00\",\n" +
                            "    \"telefone\": \"00000000000\",\n" +
                            "    \"numero\": \"00000000000\",\n" +
                            "    \"email\": \"day@gmail.com\",\n" +
                            "    \"estado\": \"pr\",\n" +
                            "    \"cidade\": \"foz\",\n" +
                            "    \"logradouro\": \"santa\",\n" +
                            "    \"numero\": \"123\",\n" +
                            "    \"cep\": \"00000-000\",\n" +
                            "    \"pais\": \"br\",\n" +
                            "}")
                    .contentType(ContentType.JSON)
                    .when()
                    .put("/fornecedores/9")
                    .then()
                    .statusCode(400);
        }
        @Test
        public void disableFornecedor() {
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .when()
                    .put("/forecedores/disable/3")
                    .then()
                    .statusCode(404);
        }


    @Test
    public void verifyEmail() throws Exception {
        Pageable pageable = PageRequest.of(1, 10);
        Fornecedor fornecedor = new Fornecedor("anna", "12345", "123450","123", "anna@gmail.com", "858622660","parana", "casa", "foz", "brasil");

        List<Fornecedor> fornecedorList = new ArrayList<>();
        fornecedorList.add(fornecedor);

        Page<Fornecedor> fornecedorPage = new PageImpl(fornecedorList);

        when(fornecedorService.findAll(pageable)).thenReturn(fornecedorPage);
        this.mockMvc.perform(get("/api/fornecedores")).andExpect(status().isOk());
        assertThat(fornecedorPage.getContent().get(0).getEmail(), equalTo("anna@gmail.com"));
    }

    @Test
    public void verifyCnpj() throws Exception {
        Pageable pageable = PageRequest.of(1, 10);
        Fornecedor fornecedor = new Fornecedor("day", "01.234.567/0001-23","456123","12","874512","85880","pr","santos","foz","br");

        List<Fornecedor> fornecedorList = new ArrayList<>();
        fornecedorList.add(fornecedor);

        Page<Fornecedor> fornecedorPage = new PageImpl(fornecedorList);

        when(fornecedorService.findAll(pageable)).thenReturn(fornecedorPage);
        this.mockMvc.perform(get("/api/fornecedores")).andExpect(status().isOk());
        assertThat(fornecedorPage.getContent().get(0).getCnpj(), equalTo("01.234.567/0001-23"));
    }
}

