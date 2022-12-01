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

    public ResponseEntity<?> handleFavoritos(FavoritoDTO favorito) {

        Produto teste = produtoRepository.getById(favorito.getIdProduto());
        System.out.println(teste);

        if(this.favoritosRepository.existsFavoritosByProdutoIdAndUsuarioId(favorito.getIdProduto(), favorito.getIdUser()) != null){
            Favoritos favoritos = new Favoritos();

            favoritos.setUsuario(usuarioRepository.getById(favorito.getIdUser()));
            favoritos.setProduto(produtoRepository.getById(favorito.getIdProduto()));
            saveFavorito(favoritos);
            return ResponseEntity.ok().body("Favorito cadastrado com sucesoo!");
        }else{
            this.favoritosRepository.deleteByUserIdAndProductId(favorito.getIdProduto(), favorito.getIdUser());
            return ResponseEntity.badRequest().body("Favorito já cadastrado.");
        }
    }

    public Page<Favoritos> findUserFavoritos(HttpHeaders headers, Pageable pageable) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        Long idUser = this.tokenService.getUserId(token.substring(7, token.length()));
        return this.favoritosRepository.findFavoritosByUsuarioId(idUser, pageable);
    }

    @Transactional
    protected void saveFavorito(Favoritos favorito){
        this.favoritosRepository.save(favorito);
    }

}
