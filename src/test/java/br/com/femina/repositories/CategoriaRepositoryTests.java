package br.com.femina.repositories;

import br.com.femina.entities.Categorias;
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
public class CategoriaRepositoryTests {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @Order(1)
    @DisplayName("Inserir Categoria")
    public void insertCategoria() {
        Categorias categorias = new Categorias("categoria");
        categoriaRepository.save(categorias);
        int countCategorias = categoriaRepository.findAll().size();
        assertThat(countCategorias).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("Inserir Categoria com valor nulo")
    public void insertCategoriaNull() {
        Categorias categorias = new Categorias(null);
        AtomicInteger countCategorias = new AtomicInteger();
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            categoriaRepository.save(categorias);
            countCategorias.set(categoriaRepository.findAll().size());
        });
        assertThat(exception.getMessage()).isNotNull();
        assertThat(countCategorias.get()).isEqualTo(0);
    }

    @Test
    @Order(3)
    @DisplayName("Inserir Categoria com o mesmo valor(unique)")
    public void insertExistingCategoria() {
        Categorias categorias = new Categorias("categoria");
        categoriaRepository.save(categorias);
        categoriaRepository.save(categorias);
        int countCategorias = categoriaRepository.findAll().size();
        assertThat(countCategorias).isEqualTo(1);
    }

    @Test
    @Order(4)
    @DisplayName("Listar Categorias")
    public void listCategorias() {
        Categorias categorias = new Categorias("categoria");
        categoriaRepository.save(categorias);
        List<Categorias> categoriasList = categoriaRepository.findAll();
        assertThat(categoriasList.size()).isGreaterThanOrEqualTo(1);
    }

}
