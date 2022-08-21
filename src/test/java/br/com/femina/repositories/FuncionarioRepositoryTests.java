package br.com.femina.repositories;

import br.com.femina.entities.Cargo;
import br.com.femina.entities.Funcionario;
import br.com.femina.entities.Sexo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FuncionarioRepositoryTests {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Test
    public void insertFuncionario() {
        BigDecimal salario = new BigDecimal(12345);
        Funcionario funcionario = new Funcionario();

        funcionario.setCargo(Cargo.FUNCIONARIO);
        funcionario.setSalario(salario);

        funcionario.setNome("teste");
        funcionario.setLogin("testeLogin");
        funcionario.setSenha("1234");
        funcionario.setSexo(Sexo.MASCULINO);
        funcionario.setDataNascimento(new Date());
        funcionario.setCpf("000.000.000-00");
        funcionario.setEmail("teste@gmail.com");
        funcionario.setTelefone("00000000000");
        funcionario.setPais("Brasil");
        funcionario.setEstado("Parana");
        funcionario.setCidade("Foz do Iguaçu");
        funcionario.setLogradouro("Vila Yolanda");
        funcionario.setNumero("48");
        funcionario.setCep("00000-000");

        funcionarioRepository.save(funcionario);
        Integer totalFuncionarios = funcionarioRepository.findAll().size();
        assertTrue(totalFuncionarios > 0);
    }

    @Test
    public void insertExistingFuncionario(){
        BigDecimal salario = new BigDecimal(12345);
        Funcionario funcionario = new Funcionario();

        funcionario.setCargo(Cargo.FUNCIONARIO);
        funcionario.setSalario(salario);

        funcionario.setNome("teste");
        funcionario.setLogin("testeLogin");
        funcionario.setSenha("1234");
        funcionario.setSexo(Sexo.MASCULINO);
        funcionario.setDataNascimento(new Date());
        funcionario.setCpf("000.000.000-00");
        funcionario.setEmail("teste@gmail.com");
        funcionario.setTelefone("00000000000");
        funcionario.setPais("Brasil");
        funcionario.setEstado("Parana");
        funcionario.setCidade("Foz do Iguaçu");
        funcionario.setLogradouro("Vila Yolanda");
        funcionario.setNumero("48");
        funcionario.setCep("00000-000");

        funcionarioRepository.save(funcionario);
        funcionarioRepository.save(funcionario);

        Integer totalFuncionarios = funcionarioRepository.findAll().size();
        assertEquals(1, totalFuncionarios);
    }

    @Test
    public void listFuncionarios() {
        List<Funcionario> listFuncionarios = funcionarioRepository.findAll();
        assertNotNull(listFuncionarios);
    }
}
