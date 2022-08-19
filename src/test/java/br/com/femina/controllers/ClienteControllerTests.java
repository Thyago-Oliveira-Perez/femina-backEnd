package br.com.femina.controllers;


import br.com.femina.entities.Cliente;
import br.com.femina.entities.Sexo;
import br.com.femina.repositories.ClienteRepository;
import br.com.femina.services.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Comment;
import org.hibernate.type.LocalDateType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Get client by id")
    public void getByIdClient() throws Exception {

        Cliente client = new Cliente();

        client.setId((long) 1);
        client.setCadastrado(LocalDateTime.now());
        client.setAtualizado(LocalDateTime.now());
        client.setIsActive(true);
        client.setNome("teste01");
        client.setLogin("testeLogin00");
        client.setSenha("1234");
        client.setSexo(Sexo.MASCULINO);
        client.setDataNascimento(new Date());
        client.setCpf("000.000.000-00");
        client.setEmail("teste@gmail.com");
        client.setTelefone("0000-0000");
        client.setPais("Brasil");
        client.setEstado("Parana");
        client.setCidade("Foz do Iguaçu");
        client.setLogradouro("Vila Yolanda");
        client.setNumero("48");
        client.setCep("00000-000");


        doReturn(Optional.of(client)).when(clienteService).findById(1L);

        this.mockMvc.perform(get("/api/clientes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("get by non-existent id")
    public void getByIdNotRegistered() throws Exception{

        Cliente client = new Cliente();

        doReturn(Optional.of(client)).when(clienteService).findById(1L);

        this.mockMvc.perform(get("/api/clientes/{id}", 1L))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("\"id\":null")));
    }

    @Test
    @DisplayName("insert one client")
    public void postClient() throws Exception {
        Cliente client = new Cliente();

        doNothing().when(clienteService).insert(client);

        client.setId((long) 1);
        client.setCadastrado(LocalDateTime.now());
        client.setAtualizado(LocalDateTime.now());
        client.setIsActive(true);
        client.setNome("teste01");
        client.setLogin("testeLogin00");
        client.setSenha("1234");
        client.setSexo(Sexo.MASCULINO);
        client.setDataNascimento(new Date());
        client.setCpf("000.000.000-00");
        client.setEmail("teste@gmail.com");
        client.setTelefone("0000-0000");
        client.setPais("Brasil");
        client.setEstado("Parana");
        client.setCidade("Foz do Iguaçu");
        client.setLogradouro("Vila Yolanda");
        client.setNumero("48");
        client.setCep("00000-000");

        this.mockMvc.perform(
                        post("/api/clientes")
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}
