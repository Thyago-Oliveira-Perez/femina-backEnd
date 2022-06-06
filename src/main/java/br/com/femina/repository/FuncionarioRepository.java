package br.com.femina.repository;

import br.com.femina.entity.Funcionario;
import br.com.femina.entity.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    public Page<Funcionario> findAllByHabilitado(Boolean habilitado, Pageable pageable);

    @Modifying
    @Query("update Funcionario funcionario " +
            "set funcionario.habilitado = :dataExcluido " +
            "where funcionario.id = :marca")
    public void updateStatus(
            @Param("dataExcluido") LocalDateTime dataExcluido,
            @Param("marca") Long idMarca
    );

}
