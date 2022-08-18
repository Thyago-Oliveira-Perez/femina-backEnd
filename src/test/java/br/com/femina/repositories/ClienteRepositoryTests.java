package br.com.femina.repositories;

import br.com.femina.entities.Cliente;
import br.com.femina.entities.Sexo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClienteRepositoryTests {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Inserir 1 cliente")
    public void insertClient(){
        Cliente client0 = new Cliente();

        client0.setNome("teste");
        client0.setLogin("testeLogin");
        client0.setSenha("1234");
        client0.setSexo(Sexo.MASCULINO);
        client0.setDataNascimento(new Date());
        client0.setCpf("000.000.000-00");
        client0.setEmail("teste@gmail.com");
        client0.setTelefone("0000-0000");
        client0.setPais("Brasil");
        client0.setEstado("Parana");
        client0.setCidade("Foz do Iguaçu");
        client0.setLogradouro("Vila Yolanda");
        client0.setNumero("48");
        client0.setCep("00000-000");

        clienteRepository.save(client0);
        Integer countOfClients = clienteRepository.findAll().size();
        assertTrue(countOfClients > 0);
    }

    @Test
    @DisplayName("Inserir e listar")
    public void listClients(){
        Cliente client = new Cliente();

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

        clienteRepository.save(client);
        int countOfClients = clienteRepository.findAll().size();

        assertEquals(1, countOfClients);
    }

}
