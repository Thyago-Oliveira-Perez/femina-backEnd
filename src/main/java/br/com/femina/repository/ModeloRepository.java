package br.com.femina.repository;

import br.com.femina.entity.Modelo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    @Query(value = "SELECT modelo from Modelo modelo" +
            " where modelo.habilitado = true",
            countQuery = "select count(1) From Modelo",
            nativeQuery = true)
    public void listAllActive (Pageable pageable);

    @Modifying
    @Query(" update Modelo modelo " +
           " set modelo.habilitado = : habilitado " +
            "where modelo.id = : modelo")
    public void disable (
            @Param("habilitado") Boolean habilitado,
            @Param ("modelo" ) long id_modelo
    );




}