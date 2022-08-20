package br.com.femina.repositories;
import br.com.femina.entities.Marca;
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
public class MarcaRepositoryTests {

    @Autowired
    private MarcaRepository marcaRepository;

    @Test
    public void insertMarca() {
        Marca marca = new Marca("beira rio");
        marcaRepository.save(marca);
        int countMarca = marcaRepository.findAll().size();
        assertEquals(1, countMarca);
    }

    @Test
    public void insertExistingMarca() {
        Marca marca = new Marca("Victoria Secrets");
        marcaRepository.save(marca);
        marcaRepository.save(marca);
        int countCor = marcaRepository.findAll().size();
        assertEquals(1, countCor);
    }

    @Test
    public void listMarca() {
        List<Marca> corsMarca = marcaRepository.findAll();
        assertNotNull(corsMarca);
    }


}
