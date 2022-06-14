package br.com.femina.services;

import br.com.femina.entities.Categorias;
import br.com.femina.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public void insert(Categorias categorias) {
        this.categoriaRepository.save(categorias);
    }

    public Optional<Categorias> findById(Long id){
        return this.categoriaRepository.findById(id);
    }

    public Page<Categorias> findAll(Pageable pageable){
        return this.categoriaRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Long id, Categorias categorias) {
        if(id ==  categorias.getId()) {
            this.categoriaRepository.delete(categorias);
        } else {
            throw new RuntimeException();
        }
    }

}
