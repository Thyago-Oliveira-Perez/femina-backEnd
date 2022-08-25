package br.com.femina.services;

import br.com.femina.entities.Categorias;
import br.com.femina.repositories.CategoriaRepository;
import br.com.femina.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public void insert(Categorias categorias) {
        this.categoriaRepository.save(categorias);
    }

    public Optional<Categorias> findById(Long id){
        return this.categoriaRepository.findById(id).isPresent() ? this.categoriaRepository.findById(id) : null;
    }

    public Page<Categorias> findAll(Pageable pageable){
        return this.categoriaRepository.findAll(pageable);
    }

    @Transactional
    public boolean updateStatus(Long id){
        List<Long> listaDeIds = this.categoriaRepository.findAllIds();

        if(listaDeIds.contains(id)){
            this.produtoRepository.updateByIdCategoria(id);
            this.categoriaRepository.updateStatus(id);
            return true;
        }else{
            return false;
        }
    }
}
