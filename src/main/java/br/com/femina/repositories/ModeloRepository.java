package br.com.femina.repositories;

import br.com.femina.entities.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    public Page<Modelo> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Modelo modelo " +
            "SET modelo.isActive = false " +
            "WHERE modelo.id = :id")
    public void updateStatus(@Param("id") Long id);

}
