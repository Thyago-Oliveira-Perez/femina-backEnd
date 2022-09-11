package br.com.femina.services;

import br.com.femina.entities.Favoritos;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.ProdutoRepository;
import br.com.femina.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FavoritosService {

    @Autowired
    private FavoritosRepository favoritosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public boolean insert(Long idProduto, Long idUser) {
        if(!this.favoritosRepository.existsFavoritosByProdutoIdAndUsuarioId(idProduto, idUser)){
            Favoritos favoritos = new Favoritos();
            favoritos.setUsuario(usuarioRepository.getById(idUser));
            favoritos.setProduto(produtoRepository.getById(idProduto));
            this.favoritosRepository.save(favoritos);
            return true;
        }else{
            return false;
        }
    }

    public Optional<Favoritos> findById(Long id) {
        return this.favoritosRepository.findById(id).isPresent() ? this.favoritosRepository.findById(id) : null;
    }

    public Page<Favoritos> findUserFavoritos(Long idUser, Pageable pageable) {
        return this.favoritosRepository.findFavoritosByUsuarioId(idUser, pageable);
    }

    @Transactional
    public boolean delete(Long id) {
        if (this.favoritosRepository.existsById(id)){
            this.favoritosRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
