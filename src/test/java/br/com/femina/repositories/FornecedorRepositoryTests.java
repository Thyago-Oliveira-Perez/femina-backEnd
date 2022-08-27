package br.com.femina.repositories;
import br.com.femina.entities.Fornecedor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FornecedorRepositoryTests {

    @Autowired
    private FornecedorRepository FornecedorRepositoy;

    @Test
    public void insertFornecedor() {
        Fornecedor fav = new Fornecedor("lojinha","89899","989898", "99905406", "mj@gmal.com", "pr","aduana", "foz","foz", "br");
        FornecedorRepositoy.save(fav);
        int countFavoritos = FornecedorRepositoy.findAll().size();
        assertEquals(1, countFavoritos);
    }
    @Test
    public void insertExistingFornecedor() {
        Fornecedor fornecedor = new Fornecedor("lojinha","89899","989898", "99905406", "mj@gmal.com", "pr","aduana", "foz","foz", "br");
        FornecedorRepositoy.save(fornecedor);
        FornecedorRepositoy.save(fornecedor);
        int countFornecedor = FornecedorRepositoy.findAll().size();
        assertEquals(1, countFornecedor);
    }

    @Test
    public void listFornecedor() {
        List<Fornecedor> fornedorList = FornecedorRepositoy.findAll();
        assertNotNull(fornedorList);
    }

}
