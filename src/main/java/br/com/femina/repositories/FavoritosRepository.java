package br.com.femina.repositories;

import br.com.femina.entities.Favoritos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {

    public Page<Favoritos> findAllByHabilitado(Boolean habilitado, Pageable pageable);

    @Modifying
    @Query("UPDATE Favoritos favoritos " +
            "SET favoritos.habilitado = :habilitado " +
            "WHERE favoritos.id = :favoritos")
    public void disable(
            @Param("favoritos") Long idFavoritos,
            @Param("habilitado") Boolean habilitado
    );
}