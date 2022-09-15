package br.com.femina.repositories;

import br.com.femina.dto.Filters;
import br.com.femina.entities.Produto;
import br.com.femina.entities.enums.Tamanho;
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
    @Query("update Produto produto set produto.categoria.id = null where produto.categoria.id = :idCategoria")
    public void updateCategoriaByIdCategoria(@Param("idCategoria") Long id);

    @Query("FROM Produto produto " +
            "WHERE " +
            "produto.categoria.id IN :categoriaIds AND " +
            "produto.marca.id IN :marcaIds AND " +
            "produto.cor LIKE :cor AND " +
            "cast(produto.tamanho as string)  LIKE :tamanho AND " +
            "produto.isActive = :active ")
    public Page<Produto> findAllByFilters(
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
    public void updateStatus(@Param("id") Long id, @Param("status") Boolean status);

}
