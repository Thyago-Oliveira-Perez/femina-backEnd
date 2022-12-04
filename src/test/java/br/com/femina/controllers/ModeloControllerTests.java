package br.com.femina.controllers;

import br.com.femina.config.TestSecurityConfig;
import br.com.femina.entities.Modelo;
import br.com.femina.services.ModeloService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ModeloControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModeloService modeloService;

    UUID modeloId = UUID.randomUUID();

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(1)
    public void postModelo() throws Exception {
        Modelo modelo = new Modelo("teste");
        this.mockMvc.perform(post("/api/modelos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(modelo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getModelo() throws Exception {
        Pageable pageable = PageRequest.of(1,4);
        Modelo modelo = new Modelo("teste");
        List<Modelo> modelosList = List.of(modelo);
        Page<Modelo> modelosPage = new PageImpl<Modelo>(modelosList);
        when(modeloService.findAll(pageable)).thenReturn(modelosPage);
        this.mockMvc.perform(get("/api/modelos"))
                .andExpect(status().isOk());
        assertThat(modelosPage.getContent().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(3)
    public void getModeloById() throws Exception {
        Modelo modelo = new Modelo("teste");
        modelo.setId(modeloId);
        when(modeloService.findById(modeloId)).thenReturn(ResponseEntity.ok(modelo));
        this.mockMvc.perform(get("/api/modelos/"+modeloId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void disableModelo() throws Exception {
        this.mockMvc.perform(put("/api/modelos/disable/"+modeloId))
                .andExpect(status().isOk());
    }

}
