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
    @Modifying
    @Query("update Fornecedor fornecedor " +
            "set fornecedor.habilitado = :habilitado " +
            "where fornecedor.id = :idFornecedor")

    public void updateStatus(
            @Param("habilitado") Boolean habilitado,
            @Param("idFornecedor") Long idFornecedor
    );

    Page<Fornecedor> findAll(Pageable pageable);

}
