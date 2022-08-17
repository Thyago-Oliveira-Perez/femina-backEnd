package br.com.femina.repositories;

import br.com.femina.entities.Modelo;
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
public class ModeloRepositoryTests {

    @Autowired
    private ModeloRepository modeloRepository;

    @Test
    public void insertModelo() {
        Modelo modelo = new Modelo("jogger");
        modeloRepository.save(modelo);
        int countModelo = modeloRepository.findAll().size();
        assertEquals(1, countModelo);
    }

    @Test
    public void insertExistingModelo() {
        Modelo modelo = new Modelo("camiseta");
        modeloRepository.save(modelo);
        modeloRepository.save(modelo);
        int countModelo = modeloRepository.findAll().size();
        assertEquals(1, countModelo);
    }

    @Test
    public void listModelo() {
        List<Modelo> corsModelo = modeloRepository.findAll();
        assertNotNull(corsModelo);
    }

}
