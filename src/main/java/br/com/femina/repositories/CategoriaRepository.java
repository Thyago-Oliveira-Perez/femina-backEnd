package br.com.femina.repositories;

import br.com.femina.entities.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categorias, Long> {


}
