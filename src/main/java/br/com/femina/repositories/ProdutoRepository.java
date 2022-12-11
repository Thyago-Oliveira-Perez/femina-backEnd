package br.com.femina.repositories;

import br.com.femina.entities.Produto;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID>, JpaSpecificationExecutor<Produto> {

    @Modifying
    @Query("UPDATE Produto produto SET produto.categoria.id = null WHERE produto.categoria.id = :idCategoria")
    void updateCategoriaByIdCategoria(@Param("idCategoria") UUID id);

    Page<Produto> findAllByIsActiveOrderByCadastradoAsc(Boolean isActive, Pageable pageable);

    @Modifying
    @Query("UPDATE Produto produto " +
            "SET produto.isActive = :status " +
            "WHERE produto.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") Boolean status);

    Page<Produto> findAll(Specification<Produto> filters, Pageable pageable);
    Page<Produto> findAll(Pageable pageable);
}
