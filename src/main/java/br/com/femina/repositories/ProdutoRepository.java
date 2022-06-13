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
    @Query("UPDATE Produto produto " +
            "SET produto.habilitado = :habilitado " +
            "WHERE produto.id = :id")
    public void disable(@Param("habilitado")boolean habilitado,
                       @Param("id")Long id);

    @Query("SELECT count(id), nome FROM " +
            "Produto WHERE habilitado = true " +
            "GROUP BY nome")
    public List<Page<Produto>> visualizarTudo(Pageable pageable);
}
