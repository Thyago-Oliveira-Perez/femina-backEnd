package br.com.femina.repositories;

import br.com.femina.entities.Categorias;
import br.com.femina.entities.Cor;
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
public class CorRepositoryTests {

    @Autowired
    private CorRepository corRepository;

    @Test
    public void insertCor() {
        Cor cor = new Cor("azul","#uuyyu");
        corRepository.save(cor);
        int countCor = corRepository.findAll().size();
        assertEquals(1, countCor);
    }

    @Test
    public void insertExistingCor() {
        Cor cor = new Cor("cor", "#uuyyu");
        corRepository.save(cor);
        corRepository.save(cor);
        int countCor = corRepository.findAll().size();
        assertEquals(1, countCor);
    }

    @Test
    public void listCor() {
        List<Cor> corsList = corRepository.findAll();
        assertNotNull(corsList);
    }




}
