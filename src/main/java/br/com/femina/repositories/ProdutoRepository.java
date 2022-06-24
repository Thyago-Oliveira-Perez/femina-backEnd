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
    @Query("update Produto produto set  produto.categoria.id = null where produto.categoria.id = :idCategoria")
    public void updateByIdCategoria(@Param("idCategoria") Long id);

}
