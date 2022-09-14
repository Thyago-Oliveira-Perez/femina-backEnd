package br.com.femina.repositories;

import br.com.femina.entities.Modelo;
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
public class ModeloRepositoryTests {

    @Autowired
    private ModeloRepository modeloRepository;

    @Test
    @Order(1)
    @DisplayName("Inserir Modelo")
    public void insertModelo() {
        Modelo modelo = new Modelo("jogger");
        modeloRepository.save(modelo);
        int countModelo = modeloRepository.findAll().size();
        assertThat(countModelo).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("Inserir Modelo com valor nulo")
    public void insertModeloNull() {
        Modelo modelo = new Modelo(null);
        AtomicInteger countModelo = new AtomicInteger();
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            modeloRepository.save(modelo);
            countModelo.set(modeloRepository.findAll().size());
        });
        assertThat(exception.getMessage()).isNotNull();
        assertThat(countModelo.get()).isEqualTo(0);
    }

    @Test
    @Order(3)
    @DisplayName("Inserir Modelo com o mesmo valor(unique)")
    public void insertExistingModelo() {
        Modelo modelo = new Modelo("camiseta");
        modeloRepository.save(modelo);
        modeloRepository.save(modelo);
        int countModelo = modeloRepository.findAll().size();
        assertThat(countModelo).isEqualTo(1);
    }

    @Test
    @Order(4)
    @DisplayName("Listar Modelos")
    public void listModelo() {
        Modelo modelo = new Modelo("jogger");
        modeloRepository.save(modelo);
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList.size()).isGreaterThanOrEqualTo(1);
    }

}
