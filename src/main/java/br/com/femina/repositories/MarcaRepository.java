package br.com.femina.repositories;

import br.com.femina.entities.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    @Modifying
    @Query("update Marca marca " +
            "set marca.habilitado = :dataExcluido " +
            "where marca.id = :marca")

    public void updateStatus(
            @Param("dataExcluido")LocalDateTime dataExcluido,
            @Param("marca") Long idMarca
            );

    Page<Marca> findAll(Pageable pageable);
}