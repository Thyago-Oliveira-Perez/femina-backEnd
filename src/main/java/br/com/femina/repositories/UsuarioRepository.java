package br.com.femina.repositories;

import br.com.femina.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("FROM Usuario usuario WHERE usuario.email = :input OR usuario.login = :input")
    Optional<Usuario> findByLoginOrEmail(@Param("input")String input);

    @Modifying
    @Query("UPDATE Usuario usuario SET usuario.isActive = false WHERE usuario.id = :id")
    void disable(@Param("id") Long id);
}
