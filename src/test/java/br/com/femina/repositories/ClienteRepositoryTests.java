package br.com.femina.repositories;

import br.com.femina.entities.enums.Sexo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClienteRepositoryTests {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void shoudlInsertOneClient(){

        int oldCountOfClients = clienteRepository.findAll().size();

        Cliente client = new Cliente();

        client.setNome("teste");
        client.setLogin("testeLogin");
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

        this.clienteRepository.save(client);

        int newCountOfClients = clienteRepository.findAll().size();

        assertTrue(newCountOfClients > oldCountOfClients);
    }
    @Test
    public void shouldFindOneClientById(){

        Cliente client = new Cliente();

        client.setNome("teste");
        client.setLogin("testeLogin");
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

        this.clienteRepository.save(client);

        assertNotNull(this.clienteRepository.findById(1L));
    }
    @Test
    public void shouldDisableOneClient(){
        Cliente client = new Cliente();

        client.setNome("teste");
        client.setLogin("testeLogin");
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

        this.clienteRepository.save(client);

        boolean statusNewClient = this.clienteRepository.findById(1L).get().getIsActive();

        this.clienteRepository.updateStatus(1L);

        boolean statusUpdatedClient = this.clienteRepository.findById(1L).get().getIsActive();

        assertTrue(statusNewClient != statusUpdatedClient);
    }
    @Test
    public void shouldListAllClientActive(){

        Pageable pageable = PageRequest.of(1,5);

        Cliente client = new Cliente();

        client.setNome("teste");
        client.setLogin("testeLogin");
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

        Cliente client1 = new Cliente();

        client1.setLogin("testeLogin");
        client1.setNome("teste");
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

        this.clienteRepository.save(client1);
        this.clienteRepository.save(client);

        this.clienteRepository.updateStatus(2L);

        int countOfActiveClients = this.clienteRepository.findAllByIsActive(pageable, true).getSize();
        int countOfAllClients = this.clienteRepository.findAll().size();

        assertTrue(countOfActiveClients != countOfAllClients);
    }
    @Test
    public void shouldListClients(){

        Cliente client = new Cliente();

        client.setNome("teste");
        client.setLogin("testeLogin");
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

        this.clienteRepository.save(client);

        int numberOfClients = this.clienteRepository.findAll().size();

        assertTrue(numberOfClients > 0);
    }
    @Test
    public void shouldUpdateOneClient(){

        Cliente client = new Cliente();

        client.setNome("teste");
        client.setLogin("testeLogin");
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

        this.clienteRepository.save(client);

        Cliente newClient = this.clienteRepository.findById(1L).get();

        newClient.setNome("nomeNovo");

        this.clienteRepository.save(newClient);

        assertNotEquals(client.getNome(), newClient.getNome());
    }
}
