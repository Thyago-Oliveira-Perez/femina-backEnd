package br.com.femina.repositories;

import br.com.femina.entities.Marca;
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
public class MarcaRepositoryTests {

    @Autowired
    private MarcaRepository marcaRepository;

    @Test
    @Order(1)
    @DisplayName("Inserir Marca")
    public void insertMarca() {
        Marca marca = new Marca("beira rio");
        marcaRepository.save(marca);
        int countMarca = marcaRepository.findAll().size();
        assertThat(countMarca).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("Inserir Marca com valor nulo")
    public void insertMarcaNull() {
        Marca marca = new Marca(null);
        AtomicInteger countMarca = new AtomicInteger();
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            marcaRepository.save(marca);
            countMarca.set(marcaRepository.findAll().size());
        });
        assertThat(exception.getMessage()).isNotNull();
        assertThat(countMarca.get()).isEqualTo(0);
    }

    @Test
    @Order(3)
    @DisplayName("Inserir Marca com o mesmo valor(unique)")
    public void insertExistingMarca() {
        Marca marca = new Marca("Vitoria Secret");
        marcaRepository.save(marca);
        marcaRepository.save(marca);
        int countMarca = marcaRepository.findAll().size();
        assertThat(countMarca).isEqualTo(1);
    }

    @Test
    @Order(4)
    @DisplayName("Listar Marcas")
    public void listMarca() {
        Marca marca = new Marca("Vitoria Secret");
        marcaRepository.save(marca);
        List<Marca> marcaList = marcaRepository.findAll();
        assertThat(marcaList.size()).isGreaterThanOrEqualTo(1);
    }

}
