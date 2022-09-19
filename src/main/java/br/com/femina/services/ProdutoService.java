package br.com.femina.services;

import br.com.femina.dto.Filters;
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

    public boolean insert(Produto produto){
        if(!this.produtoRepository.existsById(produto.getId())){
            saveProduto(produto);
            return true;
        }else{
            return false;
        }
    }

    public Optional<Produto> findById(Long id){

        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.isPresent() ? produto : null;
    }

    public boolean update(Long id, Produto produto) {
        if(this.produtoRepository.existsById(id) && id.equals(produto.getId())){
            saveProduto(produto);
            return true;
        }else{
            return false;
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

    public boolean updateStatusById(Long id) {
        if(this.produtoRepository.existsById(id)){
            Boolean status = this.produtoRepository.getById(id).getIsActive();
            changeStatus(id, !status);
            return true;
        }else{
            return false;
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

}
