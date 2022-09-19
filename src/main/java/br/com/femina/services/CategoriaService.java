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

    public boolean insert(Categorias categoria) {
        if(!this.categoriaRepository.existsById(categoria.getId())) {
            saveCategoria(categoria);
            return true;
        }else{
            return false;
        }
    }

    public Optional<Categorias> findById(Long id){

        Optional<Categorias> categoria = this.categoriaRepository.findById(id);

        return categoria.isPresent() ? categoria : null;
    }

    public Page<Categorias> findAll(Pageable pageable){
        return this.categoriaRepository.findAll(pageable);
    }

    public boolean updateStatusById(Long id){
        if(this.categoriaRepository.existsById(id)){
            this.produtoRepository.updateCategoriaByIdCategoria(id);
            Boolean status = this.categoriaRepository.getById(id).getIsActive();
            updateStatus(id, !status);
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    protected void saveCategoria(Categorias categoria){
        this.categoriaRepository.save(categoria);
    }

    @Transactional
    protected void updateStatus(Long id, Boolean status){
        this.categoriaRepository.updateStatus(id, status);
    }
}
