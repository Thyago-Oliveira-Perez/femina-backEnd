package br.com.femina.repositories;

import br.com.femina.dto.produto.ProdutoResponse;
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

//    @Query("FROM Produto produto " +
//            "WHERE " +
//            "produto.categoria.id IN :categoriaIds AND " +
//            "produto.marca.id IN :marcaIds AND " +
//            "produto.cor LIKE :cor AND " +
//            "CAST(produto.tamanho as string) LIKE :tamanho AND " +
//            "produto.isActive = :active")
//    Page<Produto> findAllByFilters(
//            @Param("categoriaIds") List<Long> categoriaIds,
//            @Param("marcaIds") List<Long> marcaIds,
//            @Param("cor") String cor,
//            @Param("tamanho") String tamanho,
//            Pageable pageable,
//            @Param("active") Boolean active
//    );

    @Query("SELECT " +
            "new br.com.femina.dto.produto.ProdutoResponse (" +
                "p.id,\n" +
                "p.nome,\n" +
                "p.codigo,\n" +
                "p.valor,\n" +
                "p.marca,\n" +
                "p.categoria,\n" +
                "p.modelo,\n" +
                "p.fornecedor,\n" +
                "p.tamanho,\n" +
                "p.cor,\n" +
                "p.descricao,\n" +
                "p.imagem,\n" +
                "p.destaque," +
                "new string[]\n" +
            ")" +
            "FROM " +
            "   Produto p " +
            "WHERE " +
                "produto.categoria.id IN :categoriaIds AND " +
                "produto.marca.id IN :marcaIds AND " +
                "produto.cor LIKE :cor AND " +
            "CAST(produto.tamanho as string) LIKE :tamanho AND " +
            "produto.isActive = :active"
    )
    Page<ProdutoResponse> findAllByFilters(
            @Param("categoriaIds") List<Long> categoriaIds,
            @Param("marcaIds") List<Long> marcaIds,
            @Param("cor") String cor,
            @Param("tamanho") String tamanho,
            Pageable pageable,
            @Param("active") Boolean active
    );

    @Modifying
    @Query("UPDATE Produto produto " +
            "SET produto.isActive = :status " +
            "WHERE produto.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") Boolean status);

    @Query("SELECT " +
            "new br.com.femina.dto.produto.ProdutoResponse (" +
            "p.id,\n" +
            "p.nome,\n" +
            "p.codigo,\n" +
            "p.valor,\n" +
            "p.marca,\n" +
            "p.categoria,\n" +
            "p.modelo,\n" +
            "p.fornecedor,\n" +
            "p.tamanho,\n" +
            "p.cor,\n" +
            "p.descricao,\n" +
            "p.imagem,\n" +
            "p.destaque," +
            "new string[]\n" +
            ")" +
            "FROM " +
            "   Produto p " +
            "WHERE " +
            "produto.categoria.id IN :categoriaIds AND " +
            "produto.marca.id IN :marcaIds AND " +
            "produto.cor LIKE :cor AND " +
            "CAST(produto.tamanho as string) LIKE :tamanho AND " +
            "produto.isActive = :active"
    )
    Page<ProdutoResponse> findAllProdutoResponse(Pageable pageable);
}
