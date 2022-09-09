package br.com.femina.repositories;

import br.com.femina.entities.Categorias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categorias, Long> {

    @Modifying
    @Query("UPDATE Categorias categorias " +
            "SET categorias.isActive = false " +
            "WHERE categorias.id = :id")
    public void updateStatus(@Param("id") Long id);

}
