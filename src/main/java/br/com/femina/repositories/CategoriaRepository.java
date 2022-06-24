package br.com.femina.repositories;

import br.com.femina.entities.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categorias, Long> {

    @Query("select id from Categorias")
    public List<Long> findAllIds();
}
