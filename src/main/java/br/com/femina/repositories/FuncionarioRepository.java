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

    @Modifying
    @Query("update Funcionario funcionario " +
            "set funcionario.habilitado = :habilitado " +
            "where funcionario.id = :idMarca")

    public void updateStatus(
            @Param("habilitado") Boolean habilitado,
            @Param("idMarca") Long idMarca
    );

    Page<Funcionario> findAll(Pageable pageable);


}
