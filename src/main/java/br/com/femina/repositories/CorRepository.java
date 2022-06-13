package br.com.femina.repositories;

import br.com.femina.entities.Cor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorRepository extends JpaRepository<Cor, Long> {
 /*   @Modifying
    @Query("UPDATE Cor cor " +
    "SET cor.habilitado =:dataHabilitado " +
    "WHERE cor.id = :cor")
    public void updateStatus(
            @Param("dataHabilitado")LocalDateTime dataHabilitado, Long id);
            @Param("cores")LocalDateTime idCores;
*/
}
