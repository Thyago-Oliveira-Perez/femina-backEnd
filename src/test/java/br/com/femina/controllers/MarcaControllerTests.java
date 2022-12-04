package br.com.femina.controllers;

import br.com.femina.config.TestSecurityConfig;
import br.com.femina.entities.Marca;
import br.com.femina.services.MarcaService;
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
public class MarcaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarcaService marcaService;

    private static ObjectMapper mapper = new ObjectMapper();

    UUID marcaId = UUID.randomUUID();

    @Test
    @Order(1)
    public void postMarca() throws Exception {
        Marca marca = new Marca("teste");
        this.mockMvc.perform(post("/api/marcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(marca))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getMarca() throws Exception {
        Pageable pageable = PageRequest.of(1,4);
        Marca marca = new Marca("teste");
        List<Marca> marcasList = List.of(marca);
        Page<Marca> marcaPage = new PageImpl<Marca>(marcasList);
        when(marcaService.findAll(pageable)).thenReturn(marcaPage);
        this.mockMvc.perform(get("/api/marcas"))
                .andExpect(status().isOk());
        assertThat(marcaPage.getContent().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(3)
    public void getMarcaById() throws Exception {
        Marca marca = new Marca("teste");
        marca.setId(marcaId);
        when(marcaService.findById(marcaId)).thenReturn(ResponseEntity.ok(marca));
        this.mockMvc.perform(get("/api/marcas/"+marcaId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void disableMarca() throws Exception {
        this.mockMvc.perform(put("/api/marcas/disable/"+marcaId))
                .andExpect(status().isOk());
    }
}
