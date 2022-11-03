package br.com.femina.controllers;

import br.com.femina.config.TestSecurityConfig;
import br.com.femina.entities.Categorias;
import br.com.femina.services.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
 import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CategoriaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(1)
    public void postCategoria() throws Exception {
        this.mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cadastrado\":\"2022-09-22T21:23:32.176151\",\"atualizado\":null,\"isActive\":true,\"nome\":\"teste\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    @Order(2)
//    public void getCategoria() throws Exception {
//        Pageable pageable = PageRequest.of(1,4);
//        Categorias categorias = new Categorias("teste");
//        List<Categorias> categoriasList = List.of(categorias);
//        Page<Categorias> categoriasPage = new PageImpl<Categorias>(categoriasList);
//        when(categoriaService.findAll(pageable)).thenReturn(categoriasPage);
//        this.mockMvc.perform(get("/api/categorias"))
//                .andExpect(status().isOk());
//        assertThat(categoriasPage.getContent().size()).isGreaterThanOrEqualTo(1);
//    }
//
//    @Test
//    @Order(3)
//    public void getCategoriaById() throws Exception {
//        Categorias categorias = new Categorias("teste");
//        categorias.setId(1L);
//        when(categoriaService.findById(1L)).thenReturn(ResponseEntity.ok(categorias));
//        this.mockMvc.perform(get("/api/categorias/"+1L))
//                .andExpect(status().isOk());
//    }

    @Test
    @Order(4)
    public void disableCategoria() throws Exception {
        this.mockMvc.perform(put("/api/categorias/disable/"+1L))
                .andExpect(status().isOk());
    }

}
