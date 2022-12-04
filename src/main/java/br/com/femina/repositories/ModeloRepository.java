package br.com.femina.repositories;

import br.com.femina.entities.Modelo;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, UUID> {

    public Page<Modelo> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Modelo modelo " +
            "SET modelo.isActive = :status " +
            "WHERE modelo.id = :id")
    public void updateStatus(@Param("id") UUID id, @Param("status") Boolean status);

}
