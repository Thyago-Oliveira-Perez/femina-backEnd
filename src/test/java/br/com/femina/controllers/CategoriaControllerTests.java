package br.com.femina.controllers;

import br.com.femina.entities.Categorias;
import br.com.femina.services.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoriaController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaControllerTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8081/api";
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;


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
    @Order(1)
    public void postCategoria() throws Exception {
        Categorias categorias = new Categorias("teste");
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/categorias")
                        .content(asJsonString(categorias))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void postCategoriaNull() throws Exception {
        Categorias categorias = new Categorias(null);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/categorias")
                        .content(asJsonString(categorias))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void getCategoria() throws Exception {
        Pageable pageable = PageRequest.of(1,4);
        Categorias categorias = new Categorias("teste");
        List<Categorias> categoriasList = List.of(categorias);
        Page<Categorias> categoriasPage = new PageImpl<Categorias>(categoriasList);
        when(categoriaService.findAll(pageable)).thenReturn(categoriasPage);
        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk());
        assertThat(categoriasPage.getContent().size(), equalTo(1));
    }

    @Test
    @Order(4)
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
    @Order(5)
    public void getCategoriasCode200() {
        RestAssured.given()
            .when()
                .get("/categorias")
            .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    public void getCategoriaByIdCode200() {
        RestAssured.given()
            .when()
                .get("/categorias/{id}",1)
            .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    @Order(7)
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
