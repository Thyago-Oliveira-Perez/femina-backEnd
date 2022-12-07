package br.com.femina.repositories;

import br.com.femina.dto.marca.MarcaResponse;
import br.com.femina.entities.Marca;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, UUID> {

    public Page<MarcaResponse> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Marca marca " +
            "SET marca.isActive = :status " +
            "WHERE marca.id = :id")
    public void updateStatus(@Param("id") UUID id, @Param("status") Boolean status);

}
