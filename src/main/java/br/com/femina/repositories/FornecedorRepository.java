package br.com.femina.repositories;

import br.com.femina.entities.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    public Page<Fornecedor> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Fornecedor fornecedor " +
            "SET fornecedor.isActive = false " +
            "WHERE fornecedor.id = :id")
    public void updateStatus(@Param("id") Long id);

}
