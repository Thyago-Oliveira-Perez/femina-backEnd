package br.com.femina.repositories;

import br.com.femina.entities.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    public Page<Funcionario> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Funcionario funcionario " +
            "SET funcionario.isActive = false " +
            "WHERE funcionario.id = :id")
    public void updateStatus(@Param("id") Long id);

}
