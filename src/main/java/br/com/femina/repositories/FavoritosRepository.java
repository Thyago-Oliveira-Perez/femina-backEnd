package br.com.femina.repositories;

import br.com.femina.entities.Favoritos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos, UUID> {

    @Query("FROM Favoritos favoritos WHERE favoritos.usuario.id = :idUsuario")
    Page<Favoritos> findFavoritosByUsuarioId(@Param("idUsuario") UUID idUsuario, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Favoritos favoritos WHERE favoritos.usuario.id = :idUsuario")
    void deleteFavoritosByUsuarioId(@Param("idUsuario") UUID idUsuario);

    @Modifying
    @Query("DELETE FROM Favoritos favoritos WHERE favoritos.produto.id = :idProduct")
    void deleteFavoritosByProductId(@Param("idProduct") UUID idProduct);

    @Modifying
    @Query("DELETE FROM Favoritos favoritos WHERE favoritos.produto.id = :idProduct AND favoritos.usuario.id = :idUsuario")
    void deleteByUserIdAndProductId(@Param("idProduct") UUID idProduct, @Param("idUsuario") UUID idUsuario);

    @Query("FROM Favoritos favoritos WHERE favoritos.produto.id = :produto_id AND favoritos.usuario.id = :usuario_id")
    Favoritos existsFavoritosByProdutoIdAndUsuarioId(@Param("produto_id") UUID produto_id, @Param("usuario_id") UUID usuario_id);

}
