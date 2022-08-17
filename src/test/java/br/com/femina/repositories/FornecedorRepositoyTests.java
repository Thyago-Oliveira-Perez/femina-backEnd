package br.com.femina.repositories;


import br.com.femina.entities.Fornecedor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FornecedorRepositoyTests {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Test
    public void insertFornecedor() {
        Fornecedor fornecedor = new Fornecedor("anna","123456","4565555", "123", "","85862266", "paraná","casa", "foz","brasil");
        fornecedorRepository.save(fornecedor);
        int countFornecedores = fornecedorRepository.findAll().size();
        assertEquals(1,countFornecedores);
    }

}
