package br.com.femina.repositories;

import br.com.femina.dto.fornecedor.FornecedorResponse;
import br.com.femina.entities.Fornecedor;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {

    @Query("SELECT " +
            "new br.com.femina.dto.fornecedor.FornecedorResponse (" +
            "f.id," +
            "f.name," +
            "f.cnpj," +
            "f.telefone," +
            "f.email) " +
            "FROM " +
            "   Fornecedor f " +
            "WHERE " +
            "f.isActive = :active")
    Page<FornecedorResponse> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Fornecedor fornecedor " +
            "SET fornecedor.isActive = :status " +
            "WHERE fornecedor.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") Boolean status);

}
