package br.com.femina.controllers;

import br.com.femina.config.TestSecurityConfig;
import br.com.femina.entities.*;
import br.com.femina.enums.Provider;
import br.com.femina.enums.Sexos;
import br.com.femina.enums.Tamanhos;
import br.com.femina.services.FavoritosService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
public class FavoritosControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoritosService favoritosService;

    private static ObjectMapper mapper = new ObjectMapper();

    Usuario usuario;
    Produto produto;

    @BeforeEach
    void initUseCase() {
        Collection<Cargos> cargos = List.of();
        usuario = new Usuario("teste",
                "teste",
                "123",
                Sexos.MASCULINO,
                "teste@teste.com",
                "999999999",
                cargos,
                Provider.LOCAL);
        Categorias categorias = new Categorias("teste");
        Marca marca = new Marca("teste");
        Modelo modelo = new Modelo("teste");
        Fornecedor fornecedor = new Fornecedor("teste", "00.000.000/0000-00","999999999","teste@email.com");
        Collection<Modelo> modelos = List.of(modelo);
        BigDecimal valor = new BigDecimal(99);
        produto = new Produto("codigo",
                "teste",
                valor, categorias,
                modelos, fornecedor,
                marca, "verde",
                Tamanhos.M, "",
                "teste", false);
    }

    @Test
    @Order(1)
    public void postFavoritos() throws Exception {
        Favoritos favoritos = new Favoritos(1L,usuario,produto);
        this.mockMvc.perform(post("/api/favoritos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(favoritos))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getFavoritosByUsuario() throws Exception {
        Pageable pageable = PageRequest.of(1,4);
        HttpHeaders headers = new HttpHeaders();
        Favoritos favoritos = new Favoritos(1L,usuario,produto);
        List<Favoritos> favoritosList = List.of(favoritos);
        Page<Favoritos> favoritosPage = new PageImpl<Favoritos>(favoritosList);
        when(favoritosService.findUserFavoritos(headers,pageable)).thenReturn(favoritosPage);
        this.mockMvc.perform(get("/api/favoritos"))
                .andExpect(status().isOk());
        assertThat(favoritosPage.getContent().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(3)
    public void deleteFavoritoById() throws Exception {
        this.mockMvc.perform(delete("/api/favoritos/"+1L))
                .andExpect(status().isOk());
    }

}
