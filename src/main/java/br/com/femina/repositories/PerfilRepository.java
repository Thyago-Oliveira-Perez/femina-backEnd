package br.com.femina.repositories;

import br.com.femina.entities.Cargos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Cargos, Long> {
}
