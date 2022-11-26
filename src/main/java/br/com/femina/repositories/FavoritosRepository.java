package br.com.femina.repositories;

import br.com.femina.entities.Favoritos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {

    @Query("FROM Favoritos favoritos WHERE favoritos.usuario.id = :idUsuario")
    Page<Favoritos> findFavoritosByUsuarioId(@Param("idUsuario") Long idUsuario, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Favoritos favoritos WHERE favoritos.usuario.id = :idUsuario")
    void deleteFavoritosByUsuarioId(@Param("idUsuario")Long idUsuario);

    @Modifying
    @Query("DELETE FROM Favoritos favoritos WHERE favoritos.produto.id = :idProduct")
    void deleteFavoritosByProductId(@Param("idProduct")Long idProduct);

    @Modifying
    @Query("DELETE FROM Favoritos favoritos WHERE favoritos.produto.id = :idProduct AND favoritos.usuario.id = : idUsuario")
    void deleteByUserIdAndProductId(@Param("idProduct")Long idProduct, @Param("idUsuario")Long idUsuario);

    boolean existsFavoritosByProdutoIdAndUsuarioId(Long produto_id, Long usuario_id);

}
