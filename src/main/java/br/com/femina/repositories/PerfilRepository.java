package br.com.femina.repositories;

import br.com.femina.entities.Cargos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Cargos, Long> {
    List<Cargos> findCargosByCargoName(String cargoName);
}
