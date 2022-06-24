package br.com.femina.services;

import br.com.femina.entities.Favoritos;
import br.com.femina.repositories.ClienteRepository;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.ProdutoRepository;
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
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public void insert(Long idProduto, Long idCliente) {
        Favoritos favoritos = new Favoritos();
        favoritos.setCliente(clienteRepository.getById(idCliente));
        favoritos.setProduto(produtoRepository.getById(idProduto));
        this.favoritosRepository.save(favoritos);
    }

    public Optional<Favoritos> findById(Long id) {
        return this.favoritosRepository.findById(id);
    }

    public Page<Favoritos> findAll(Pageable pageable) { return this.favoritosRepository.findAll(pageable); }

    @Transactional
    public void delete(Long id) {
        if (this.favoritosRepository.findById(id).isPresent()){
            this.favoritosRepository.deleteById(id);
        } else {
            throw new RuntimeException();
        }
    }

}
