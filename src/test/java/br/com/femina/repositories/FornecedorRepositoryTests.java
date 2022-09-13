package br.com.femina.repositories;

import br.com.femina.entities.Fornecedor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FornecedorRepositoryTests {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Test
    @Order(1)
    @DisplayName("Inserir Fornecedor")
    public void insertFornecedor() {
        Fornecedor fornecedor = new Fornecedor("teste", "00.000.000/0000-00","45999999999","teste@email.com");
        fornecedorRepository.save(fornecedor);
        int countFornecedores = fornecedorRepository.findAll().size();
        assertThat(countFornecedores).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("Inserir Fornecedor com os valores nulos")
    public void insertFornecedorNull() {
        Fornecedor fornecedor = new Fornecedor(null,null,null,null);
        AtomicInteger countFornecedores = new AtomicInteger();
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            fornecedorRepository.save(fornecedor);
            countFornecedores.set(fornecedorRepository.findAll().size());
        });
        assertThat(exception.getMessage()).isNotNull();
        assertThat(countFornecedores.get()).isEqualTo(0);
    }

    @Test
    @Order(3)
    @DisplayName("Inserir Fornecedor CNPJ errado")
    public void insertFornecedorCNPJWrong() {
        Fornecedor fornecedor = new Fornecedor("teste", "000000","45999999999","teste@email.com");
        AtomicInteger countFornecedores = new AtomicInteger();
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            fornecedorRepository.save(fornecedor);
            countFornecedores.set(fornecedorRepository.findAll().size());
        });
        assertThat(exception.getMessage()).isNotNull();
        assertThat(countFornecedores.get()).isEqualTo(0);
    }

    @Test
    @Order(4)
    @DisplayName("Inserir Fornecedor com os mesmos valores(unique)")
    public void insertExistingFornecedor() {
        Fornecedor fornecedor = new Fornecedor("teste", "00.000.000/0000-00","45999999999","teste@email.com");
        fornecedorRepository.save(fornecedor);
        fornecedorRepository.save(fornecedor);
        int countFornecedores = fornecedorRepository.findAll().size();
        assertThat(countFornecedores).isEqualTo(1);
    }

    @Test
    @Order(5)
    @DisplayName("Listar Fornecedores")
    public void listFornecedores() {
        Fornecedor fornecedor = new Fornecedor("teste", "00.000.000/0000-00","45999999999","teste@email.com");
        fornecedorRepository.save(fornecedor);
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList.size()).isGreaterThanOrEqualTo(1);
    }

}
