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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("Inserir 1 cliente")
    public void postClient() throws Exception {
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

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/clientes")
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Inserir clientes com os mesmos dados")
    public void clientsWithSameDatas() throws Exception{

        Cliente client = new Cliente();
        Cliente client1 = new Cliente();

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

        client1.setId((long) 1);
        client1.setCadastrado(LocalDateTime.now());
        client1.setAtualizado(LocalDateTime.now());
        client1.setIsActive(true);
        client1.setNome("teste01");
        client1.setLogin("testeLogin00");
        client1.setSenha("1234");
        client1.setSexo(Sexo.MASCULINO);
        client1.setDataNascimento(new Date());
        client1.setCpf("000.000.000-00");
        client1.setEmail("teste@gmail.com");
        client1.setTelefone("0000-0000");
        client1.setPais("Brasil");
        client1.setEstado("Parana");
        client1.setCidade("Foz do Iguaçu");
        client1.setLogradouro("Vila Yolanda");
        client1.setNumero("48");
        client1.setCep("00000-000");

        clienteService.insert(client);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/clientes")
                        .content(asJsonString(client1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
