package br.com.femina.controllers;

import br.com.femina.config.TestSecurityConfig;
import br.com.femina.dto.Filters;
import br.com.femina.entities.*;
import br.com.femina.enums.Tamanhos;
import br.com.femina.services.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProdutoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    Categorias categorias;
    Marca marca;
    Modelo modelo;
    Fornecedor fornecedor;
    Collection<Modelo> modelos;
    BigDecimal valor;

    @BeforeEach
    void initUseCase() {
        categorias = new Categorias("teste");
        marca = new Marca("teste");
        modelo = new Modelo("teste");
        fornecedor = new Fornecedor("teste", "00.000.000/0000-00","999999999","teste@email.com");
        modelos = List.of(modelo);
        valor = new BigDecimal(99);
    }

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(1)
    public void postProduto() throws Exception {
        Produto produto = new Produto("codigo",
                "teste",
                this.valor, categorias,
                modelos, fornecedor,
                marca, "verde",
                Tamanhos.M, "",
                "teste", false);
        this.mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(produto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getProduto() throws Exception {
        Pageable pageable = PageRequest.of(1,4);
        Filters filters = new Filters();
        Produto produto = new Produto("codigo",
                                        "teste",
                                            this.valor, categorias,
                                            modelos, fornecedor,
                                            marca, "verde",
                                            Tamanhos.M, "",
                                    "teste", false);
        List<Produto> produtosList = List.of(produto);
        Page<Produto> produtosPage = new PageImpl<Produto>(produtosList);
        when(produtoService.findAllByFilters(filters, pageable)).thenReturn(produtosPage);
        this.mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(produto)))
                .andExpect(status().isOk());
        assertThat(produtosPage.getContent().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(3)
    public void getModeloById() throws Exception {
        Produto produto = new Produto("codigo",
                                    "teste",
                                        this.valor, categorias,
                                        modelos, fornecedor,
                                        marca, "verde",
                                        Tamanhos.M, "",
                                "teste", false);
        produto.setId(1L);
        when(produtoService.findById(1L)).thenReturn(ResponseEntity.ok(produto));
        this.mockMvc.perform(get("/api/produtos/"+1L))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void disableProduto() throws Exception {
        this.mockMvc.perform(put("/api/produtos/disable/"+1L))
                .andExpect(status().isOk());
    }

}
