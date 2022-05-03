package br.com.femina.repository;

import br.com.femina.entity.Cor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorRepository extends JpaRepository<Cor, Long> {
}
