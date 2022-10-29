package br.com.femina.services;

import br.com.femina.entities.Favoritos;
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

    public ResponseEntity<?> insert(Long idProduto, Long idUser) {
        if(!this.favoritosRepository.existsFavoritosByProdutoIdAndUsuarioId(idProduto, idUser)){
            Favoritos favoritos = new Favoritos();
            favoritos.setUsuario(usuarioRepository.getById(idUser));
            favoritos.setProduto(produtoRepository.getById(idProduto));
            saveFavorito(favoritos);
            return ResponseEntity.ok().body("Favorito cadastrado com sucesoo!");
        }else{
            return ResponseEntity.badRequest().body("Favorito já cadastrado.");
        }
    }

    public Page<Favoritos> findUserFavoritos(HttpHeaders headers, Pageable pageable) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        Long idUser = this.tokenService.getUserId(token.substring(7, token.length()));
        return this.favoritosRepository.findFavoritosByUsuarioId(idUser, pageable);
    }

    public ResponseEntity<?> deleteById(Long id) {
        if (this.favoritosRepository.existsById(id)){
            delete(id);
            return ResponseEntity.ok().body("Favorito excluido com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void saveFavorito(Favoritos favorito){
        this.favoritosRepository.save(favorito);
    }

    @Transactional
    protected void delete(Long id){
        this.favoritosRepository.deleteById(id);
    }

}
