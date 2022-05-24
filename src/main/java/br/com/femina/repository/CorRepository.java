package br.com.femina.repository;

import br.com.femina.entity.Cor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

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
