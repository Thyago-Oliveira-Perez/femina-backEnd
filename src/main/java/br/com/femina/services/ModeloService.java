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

    public void insert (Long id, Modelo modelo){
        this.modeloRepository.save(modelo);
    }

    @Transactional
    public void update(long id, Modelo modelo){
        if(id == modelo.getId()) {
            this.modeloRepository.save(modelo);
        }else{
            throw new RuntimeException();
        }
    }

    @Transactional
    public Page<Modelo> findAll (Pageable pageable){
      return this.modeloRepository.listAllActive(pageable);
    }

    @Transactional
    public Optional<Modelo> findById (long id){
        return this.modeloRepository.findById(id);
    }

    @Transactional
    public void disable (Modelo modelo, long id){
        if(id == modelo.getId()){
            this.modeloRepository.disable(modelo.isHabilitado(), modelo.getId());
        }
    }

}
