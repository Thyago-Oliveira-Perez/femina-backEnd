package br.com.femina.services;

import br.com.femina.entities.Produto;
import br.com.femina.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Optional<Produto> findById(Long id){
        return this.produtoRepository.findById(id).isPresent() ? this.produtoRepository.findById(id) : null;
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
    public void updateStatus(Long id) {
        if(this.produtoRepository.findById(id).isPresent()){
            this.produtoRepository.updateStatus(id);
        } else {
            throw new RuntimeException();
        }
    }

}
