package br.com.femina.services;

import br.com.femina.entities.Modelo;
import br.com.femina.repositories.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional
    public void insert(Modelo modelo){
        this.modeloRepository.save(modelo);
    }

    public Optional<Modelo> findById(long id){
        return this.modeloRepository.findById(id);
    }

    public Page<Modelo> findAll(Pageable pageable){
        return this.modeloRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Long id, Modelo modelo) {
        if(id == modelo.getId()){
            this.modeloRepository.delete(modelo);
        } else {
            throw new RuntimeException();
        }
    }

}
