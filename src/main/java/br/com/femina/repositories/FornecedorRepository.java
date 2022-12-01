package br.com.femina.repositories;

import br.com.femina.dto.fornecedor.FornecedorResponse;
import br.com.femina.entities.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    @Query("SELECT " +
            "new br.com.femina.dto.fornecedor.FornecedorResponse (" +
            "f.id,\n" +
            "f.name,\n" +
            "f.cnpj,\n" +
            "f.telefone,\n" +
            "f.email" +
            ")" +
            "FROM " +
            "   Fornecedor f " +
            "WHERE " +
            "f.isActive = :active"
    )
    public Page<FornecedorResponse> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Fornecedor fornecedor " +
            "SET fornecedor.isActive = :status " +
            "WHERE fornecedor.id = :id")
    public void updateStatus(@Param("id") Long id, @Param("status") Boolean status);

}
