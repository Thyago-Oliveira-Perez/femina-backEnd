package br.com.femina.repository;

import br.com.femina.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Modifying
    @Query("UPDATE Produto produto " +
            "SET produto.habilitado =: habilitado " +
            "WHERE produto.id = :id")
    public void disable(@Param("habilitado")boolean habilitado,
                       @Param("id")Long id);

}
