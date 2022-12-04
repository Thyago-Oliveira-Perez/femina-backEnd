package br.com.femina.services;

import br.com.femina.dto.usuario.FavoritoDTO;
import br.com.femina.entities.Favoritos;
import br.com.femina.entities.Produto;
import br.com.femina.entities.Usuario;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.ProdutoRepository;
import br.com.femina.repositories.UsuarioRepository;
import br.com.femina.configurations.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FavoritosService {

    @Autowired
    private FavoritosRepository favoritosRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> insertFavoritos(FavoritoDTO favorito) {

        if(this.favoritosRepository.existsFavoritosByProdutoIdAndUsuarioId(favorito.getIdProduto(), favorito.getIdUser()) == null) {
            Favoritos favoritos = new Favoritos(
                    UUID.randomUUID(),
                    usuarioRepository.getById(favorito.getIdUser()),
                    produtoRepository.getById(favorito.getIdProduto())
            );

            saveFavorito(favoritos);
            return ResponseEntity.ok().body("Favorito cadastrado com sucesso!");
        }
        return ResponseEntity.badRequest().body("Favorito já existe no banco!");
    }

    @Transactional
    public ResponseEntity<?> deleteFavorito(FavoritoDTO favorito) {

        var teste = this.favoritosRepository.existsFavoritosByProdutoIdAndUsuarioId(favorito.getIdProduto(), favorito.getIdUser());

        if(this.favoritosRepository.existsFavoritosByProdutoIdAndUsuarioId(favorito.getIdProduto(), favorito.getIdUser()) != null){
            this.deleteByUserIdAndProductId(favorito);

            return ResponseEntity.badRequest().body("Favorito deletado com sucesso!.");
        }

        return ResponseEntity.badRequest().body("Favorito não existe no banco.");
    }

    public Page<Favoritos> findUserFavoritos(HttpHeaders headers, Pageable pageable) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        UUID idUser = this.tokenService.getUserId(token.substring(7, token.length()));
        return this.favoritosRepository.findFavoritosByUsuarioId(idUser, pageable);
    }

    @Transactional
    protected void saveFavorito(Favoritos favorito){
        this.favoritosRepository.save(favorito);
    }

    @Transactional
    protected void deleteByUserIdAndProductId(FavoritoDTO favorito){
        this.favoritosRepository.deleteByUserIdAndProductId(favorito.getIdProduto(), favorito.getIdUser());
    }
}
