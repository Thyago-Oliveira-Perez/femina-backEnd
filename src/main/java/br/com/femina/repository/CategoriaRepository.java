package br.com.femina.repository;

import br.com.femina.entity.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@RequestMapping
public interface CategoriaRepository extends JpaRepository<Categorias, Long> {


}
