package br.com.femina.repositories;

import br.com.femina.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Page<Usuario> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Usuario usuario SET usuario.isActive = :status WHERE usuario.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") Boolean status);

    boolean existsUsuarioByEmail(String userEmail);

    Optional<Usuario> findUsuarioById(Long id);
}
