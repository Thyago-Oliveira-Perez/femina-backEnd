package br.com.femina.repositories;

import br.com.femina.entities.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categorias, Long> {

    @Modifying
    @Query("UPDATE Categorias categorias " +
            "SET categorias.isActive = :status " +
            "WHERE categorias.id = :id")
    public void updateStatus(@Param("id") Long id, @Param("status") Boolean status);

}
