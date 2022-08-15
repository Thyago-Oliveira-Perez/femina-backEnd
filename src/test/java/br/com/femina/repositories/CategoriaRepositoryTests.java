package br.com.femina.repositories;

import br.com.femina.entities.Categorias;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoriaRepositoryTests {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void insertCategoria() {
        Categorias categorias = new Categorias("categoria");
        categoriaRepository.save(categorias);
        int countCategorias = categoriaRepository.findAll().size();
        assertEquals(1, countCategorias);
    }

    @Test
    public void insertExistingCategoria() {
        Categorias categorias = new Categorias("categoria");
        categoriaRepository.save(categorias);
        categoriaRepository.save(categorias);
        int countCategorias = categoriaRepository.findAll().size();
        assertEquals(1, countCategorias);
    }
}
