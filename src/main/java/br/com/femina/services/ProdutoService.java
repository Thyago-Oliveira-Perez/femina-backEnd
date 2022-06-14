package br.com.femina.services;

import br.com.femina.entities.Produto;
import br.com.femina.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Optional<Produto> findById(Long id){
        return this.produtoRepository.findById(id);
    }

    public Page<Produto> findAll(Pageable pageable){
        return this.produtoRepository.findAll(pageable);
    }

    @Transactional
    public void insert(Produto produto){
        this.produtoRepository.save(produto);
    }

    @Transactional
    public void update(Long id, Produto produto) {
        if(id == produto.getId()){
            this.produtoRepository.save(produto);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void disable(Long id, Produto produto) {
        if(id == produto.getId()){
            this.produtoRepository.delete(produto);
        } else {
            throw new RuntimeException();
        }
    }

}
