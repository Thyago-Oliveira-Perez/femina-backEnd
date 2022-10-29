package br.com.femina.controllers;

import br.com.femina.config.TestSecurityConfig;
import br.com.femina.entities.*;
import br.com.femina.entities.enums.Sexo;
import br.com.femina.services.UsuarioService;
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

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UsuarioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private static ObjectMapper mapper = new ObjectMapper();

    Collection<Perfil> perfils;

    @BeforeEach
    void initUseCase() {
        perfils = List.of();
    }

    @Test
    @Order(1)
    public void registerUsuario() throws Exception {
        Usuario usuario = new Usuario("teste",
                                    "teste",
                                    "123",
                                    Sexo.MASCULINO,
                                    "teste@teste.com",
                                    "999999999",
                                    perfils);
        this.mockMvc.perform(post("/api/usuarios/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(usuario))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void newUsuario() throws Exception {
        Collection<Perfil> perfils = List.of();
        Usuario usuario = new Usuario("teste",
                "teste",
                "123",
                Sexo.MASCULINO,
                "teste@teste.com",
                "999999999",
                perfils);
        this.mockMvc.perform(post("/api/usuarios/new-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(usuario))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void getUsuarios() throws Exception {
        Pageable pageable = PageRequest.of(1,4);
        Usuario usuario = new Usuario("teste",
                "teste",
                "123",
                Sexo.MASCULINO,
                "teste@teste.com",
                "999999999",
                perfils);
        List<Usuario> usuariosList = List.of(usuario);
        Page<Usuario> usuariosPage = new PageImpl<Usuario>(usuariosList);
        when(usuarioService.findAll(pageable)).thenReturn(usuariosPage);
        this.mockMvc.perform(get("/api/usuarios/list-all"))
                .andExpect(status().isOk());
        assertThat(usuariosPage.getContent().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(4)
    public void getUsuarioById() throws Exception {
        Usuario usuario = new Usuario("teste",
                "teste",
                "123",
                Sexo.MASCULINO,
                "teste@teste.com",
                "999999999",
                perfils);
        when(usuarioService.findById(1L)).thenReturn(ResponseEntity.ok(usuario));
        this.mockMvc.perform(get("/api/usuarios/"+1L))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void disableUsuario() throws Exception {
        this.mockMvc.perform(put("/api/usuarios/"+1L))
                .andExpect(status().isOk());
    }

}
