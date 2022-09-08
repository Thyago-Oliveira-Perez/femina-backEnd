package br.com.femina.repositories;

import br.com.femina.entities.Favoritos;
import br.com.femina.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {
    public Page<Favoritos> findFavoritosByUsuarioEquals(Usuario usuario, Pageable pageable);
}
