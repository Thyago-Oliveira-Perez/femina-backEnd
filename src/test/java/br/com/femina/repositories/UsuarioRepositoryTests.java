package br.com.femina.repositories;

import br.com.femina.entities.Usuario;
import br.com.femina.enums.Sexos;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Order(1)
    @DisplayName("Inserir Usuario")
    public void insertUsuario() {
        Usuario usuario = new Usuario("teste","teste","123", Sexos.MASCULINO, "teste@email.com", "45999999999", new ArrayList<>());
        usuarioRepository.save(usuario);
        int countUsuarios = usuarioRepository.findAll().size();
        assertThat(countUsuarios).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("Inserir Usuario com os valores nulos")
    public void insertUsuarioNull() {
        Usuario usuario = new Usuario(null,null,null,null,null,null,null);
        AtomicInteger countUsuarios = new AtomicInteger();
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            usuarioRepository.save(usuario);
            countUsuarios.set(usuarioRepository.findAll().size());
        });
        assertThat(exception.getMessage()).isNotNull();
        assertThat(countUsuarios.get()).isEqualTo(0);
    }

    @Test
    @Order(3)
    @DisplayName("Inserir Usuario com os mesmos valores(unique)")
    public void insertExistingUsuario() {
        Usuario usuario = new Usuario("teste","teste","123", Sexos.MASCULINO, "teste@email.com", "45999999999", new ArrayList<>());
        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario);
        int countUsuarios = usuarioRepository.findAll().size();
        assertThat(countUsuarios).isEqualTo(1);
    }

    @Test
    @Order(4)
    @DisplayName("Listar Usuarios")
    public void listUsuarios() {
        Usuario usuario = new Usuario("teste","teste","123", Sexos.MASCULINO, "teste@email.com", "45999999999", new ArrayList<>());
        usuarioRepository.save(usuario);
        List<Usuario> usuariosList = usuarioRepository.findAll();
        assertThat(usuariosList.size()).isGreaterThanOrEqualTo(1);
    }

}
