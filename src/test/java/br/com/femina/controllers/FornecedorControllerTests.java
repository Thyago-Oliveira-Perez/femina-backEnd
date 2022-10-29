package br.com.femina.controllers;

import br.com.femina.config.TestSecurityConfig;
import br.com.femina.entities.Fornecedor;
import br.com.femina.services.FornecedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class FornecedorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FornecedorService fornecedorService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(1)
    public void postFornecedor() throws Exception {
        Fornecedor fornecedor = new Fornecedor("teste",
                                                "00.000.000/0000-00",
                                                "999999999",
                                                "teste@teste.com");
        fornecedor.setId(1L);
        fornecedor.setIsActive(true);
        this.mockMvc.perform(post("/api/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(fornecedor))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getFornecedor() throws Exception {
        Pageable pageable = PageRequest.of(1,4);
        Fornecedor fornecedor = new Fornecedor("teste",
                                                "00.000.000/0000-00",
                                              "999999999",
                                                "teste@teste.com");
        List<Fornecedor> fornecedoresList = List.of(fornecedor);
        Page<Fornecedor> fornecedorPage = new PageImpl<Fornecedor>(fornecedoresList);
        when(fornecedorService.findAll(pageable)).thenReturn(fornecedorPage);
        this.mockMvc.perform(get("/api/fornecedores"))
                .andExpect(status().isOk());
        assertThat(fornecedorPage.getContent().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(3)
    public void getFornecedorById() throws Exception {
        Fornecedor fornecedor = new Fornecedor("teste",
                                                "00.000.000/0000-00",
                                              "999999999",
                                                "teste@teste.com");
        fornecedor.setId(1L);
        when(fornecedorService.findById(1L)).thenReturn(ResponseEntity.ok(fornecedor));
        this.mockMvc.perform(get("/api/fornecedores/"+1L))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void disableFornecedor() throws Exception {
        this.mockMvc.perform(put("/api/fornecedores/disable/"+1L))
                .andExpect(status().isOk());
    }

}
