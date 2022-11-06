package br.com.femina.repositories;

import br.com.femina.entities.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Modifying
    @Query("UPDATE Produto produto SET produto.categoria.id = null WHERE produto.categoria.id = :idCategoria")
    void updateCategoriaByIdCategoria(@Param("idCategoria") Long id);

    @Query("FROM Produto produto " +
            "WHERE " +
            "produto.categoria.id IN :categoriaIds AND " +
            "produto.marca.id IN :marcaIds AND " +
            "produto.cor LIKE :cor AND " +
            "CAST(produto.tamanho as string) LIKE :tamanho AND " +
            "produto.isActive = :active")
    Page<Produto> findAllByFilters(
            @Param("categoriaIds") List<Long> categoriaIds,
            @Param("marcaIds") List<Long> marcaIds,
            @Param("cor") String cor,
            @Param("tamanho") String tamanho,
            Pageable pageable,
            Boolean active
    );

    @Modifying
    @Query("UPDATE Produto produto " +
            "SET produto.isActive = :status " +
            "WHERE produto.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") Boolean status);

}
