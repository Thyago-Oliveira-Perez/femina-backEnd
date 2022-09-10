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
    public boolean insert(Modelo modelo){
        if(!this.modeloRepository.existsById(modelo.getId())){
            this.modeloRepository.save(modelo);
            return true;
        }else{
            return false;
        }
    }

    public Optional<Modelo> findById(long id){
        return this.modeloRepository.findById(id).isPresent() ? this.modeloRepository.findById(id) : null;
    }

    public Page<Modelo> findAll(Pageable pageable){
        return this.modeloRepository.findAllByIsActive(pageable, true);
    }

    @Transactional
    public boolean updateStatus(Long id) {
        if(this.modeloRepository.existsById(id)){
            this.modeloRepository.updateStatus(id);
            return true;
        } else {
            return false;
        }
    }

}
