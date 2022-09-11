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

    @Transactional
    public boolean insert(Produto produto){
        if(!this.produtoRepository.existsById(produto.getId())){
            this.produtoRepository.save(produto);
            return true;
        }else{
            return false;
        }
    }

    public Optional<Produto> findById(Long id){

        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.isPresent() ? produto : null;
    }

    public Page<Produto> findAll(Pageable pageable){
        return this.produtoRepository.findAllByIsActive(pageable, true);
    }

    @Transactional
    public boolean update(Long id, Produto produto) {
        if(this.produtoRepository.existsById(id) && id.equals(produto.getId())){
            this.produtoRepository.save(produto);
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public boolean updateStatus(Long id) {
        if(this.produtoRepository.existsById(id)){
            Boolean status = this.produtoRepository.getById(id).getIsActive();
            this.produtoRepository.updateStatus(id, !status);
            return true;
        }else{
            return false;
        }
    }

}
