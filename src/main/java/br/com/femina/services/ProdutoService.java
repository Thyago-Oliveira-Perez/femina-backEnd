package br.com.femina.services;

import br.com.femina.dto.Filters;
import br.com.femina.entities.Produto;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FavoritosRepository favoritosRepository;

    public ResponseEntity<?> insert(Produto produto){
        try{
            saveProduto(produto);
            return ResponseEntity.ok().body("Produto cadastrado com sucesso!");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Produto já cadastrado.");
        }
    }

    public ResponseEntity<Produto> findById(Long id){
        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.isPresent() ? ResponseEntity.ok().body(produto.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> update(Long id, Produto produto) {
        if(this.produtoRepository.existsById(id) && id.equals(produto.getId())){
            saveProduto(produto);
            return ResponseEntity.ok().body("Produto atualizado com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public Page<Produto> findAllByFilters(Filters filters, Pageable pageable){
        return this.produtoRepository.findAllByFilters(
                filters.getCategoriaIds(),
                filters.getMarcaIds(),
                filters.getCor(),
                "GG",
                pageable,
                true
        );
    }

    public ResponseEntity<?> updateStatusById(Long id) {
        if(this.produtoRepository.existsById(id)){
            String mensagem = "";
            Boolean status = this.produtoRepository.getById(id).getIsActive();
            changeStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativado";
            }
            mensagem = "desativado";
            return ResponseEntity.ok().body("Produto " + mensagem + " com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void changeStatus(Long id, Boolean status){
        this.produtoRepository.updateStatus(id, status);
    }

    @Transactional
    protected void saveProduto(Produto produto){
        this.produtoRepository.save(produto);
    }

    @Transactional
    protected void deleteFavoritosRelatedToProduct(Long id){
        this.favoritosRepository.deleteFavoritosByProductId(id);
    }

}
