package br.com.femina.repository;

import br.com.femina.entity.Favoritos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {

    @Query(value = "SELECT favoritos FROM Favoritos favoritos " +
            "WHERE favoritos.habilitado = true",
            countQuery = "SELECT count(1) FROM Favoritos",
            nativeQuery = true
    )
    public Page<Favoritos> listAllActive(Pageable page);

    @Modifying
    @Query("UPDATE Favoritos favoritos " +
            "SET favoritos.habilitado = :habilitado " +
            "WHERE favoritos.id = :favoritos")
    public void disable(
            @Param("favoritos") Long idFavoritos,
            @Param("habilitado") Boolean habilitado
    );
}
