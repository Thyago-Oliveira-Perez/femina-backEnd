package br.com.femina.repositories;

import br.com.femina.dto.categoria.CategoriaResponse;
import br.com.femina.entities.Categorias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT " +
            "NEW br.com.femina.dto.categoria.CategoriaResponse(c.id, c.nome) " +
            "FROM " +
            "   Categorias c " +
            "WHERE c.isActive =:active")
    Page<CategoriaResponse> findAllCategoriaResponse(Pageable pageable, @Param("active") boolean active);
}
