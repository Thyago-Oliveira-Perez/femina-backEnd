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

    public boolean insert(Modelo modelo){
        if(!this.modeloRepository.existsById(modelo.getId())){
            saveProduto(modelo);
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

    public boolean updateStatusById(Long id) {
        if(this.modeloRepository.existsById(id)){
            Boolean status = this.modeloRepository.getById(id).getIsActive();
            updateStatus(id, !status);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    protected void saveProduto(Modelo modelo){
        this.modeloRepository.save(modelo);
    }

    @Transactional
    protected void updateStatus(Long id, Boolean status){
        this.modeloRepository.updateStatus(id, status);
    }

}
