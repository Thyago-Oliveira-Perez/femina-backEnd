package br.com.femina.controllers;
import br.com.femina.entities.Marca;
import br.com.femina.services.MarcaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MarcaControllerTests {

    @MockBean
    private MarcaService marcaService;

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
    public void postMarca() throws Exception {
        Marca marca = new Marca("teste");
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/marcas")
                        .content(asJsonString(marca))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    //ta vindo not null
    @Test
    public void postMarcaIsNull() throws Exception {
        Marca marca = new Marca(null);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/marcas")
                        .content(asJsonString(marca))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getMarca() throws Exception {
        Pageable pageable = PageRequest.of(1,4);
        Marca marc = new Marca("teste");
        List<Marca> marcaList = List.of(marc);
        Page<Marca> marcaPage = new PageImpl<Marca>(marcaList);
        when(marcaService.findAll(pageable)).thenReturn(marcaPage);
        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk());
        assertThat(marcaPage.getContent().size(), equalTo(1));
    }

    @Test
    public void insertMarcaCode200() {
        given()
                .body("{\"nome\": \"picole\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/marcas")
                .then()
                .statusCode(200);
    }

    @Test
    public void getMarcaCode200() {
        given()
                .when()
                .get("/marcas")
                .then()
                .statusCode(200);
    }

    @Test
    public void getMarcaByIdCode200() {
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", "1")
                .when()
                .get("/marcas/{id}")
                .then()
                .extract().response();
        assertEquals (200, response.statusCode());
    }

    @Test
    public void disableMarcasCode200() {
        Response response = given()
                .header("Content-type", "application/json")
                .pathParam("id", "1")
                .when()
                .put("/marcas/disable/{id}")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
    }


}
