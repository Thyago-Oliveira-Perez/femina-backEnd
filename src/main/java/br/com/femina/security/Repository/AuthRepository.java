package br.com.femina.security.Repository;

import br.com.femina.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Usuario, Long> {

    @Query("FROM Usuario usuario WHERE usuario.email = :input OR usuario.login = :input")
    Optional<Usuario> findByLoginOrEmail(@Param("input")String input);

}