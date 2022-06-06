package br.com.femina.repository;

import br.com.femina.entity.Fornecedor;
import br.com.femina.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    public Page<Fornecedor> findAllByHabilitado(Boolean habilitado, Pageable pageable);

    @Modifying
    @Query("update Fornecedor fornecedor " +
            "set fornecedor.habilitado = :dataExcluido " +
            "where fornecedor.id = :fornecedor")
    public void updateStatus(
            @Param("dataExcluido") LocalDateTime dataExcluido,
            @Param("fornecedor") Long idFornecedor
    );

}
