package br.com.femina.services;

import br.com.femina.entities.Marca;
import br.com.femina.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public boolean insert(Marca marca) {
        if(!this.marcaRepository.existsById(marca.getId())){
            saveMarca(marca);
            return true;
        }else{
            return false;
        }
    }

    public Optional<Marca> findById(Long id) {
        Optional<Marca> marca = this.marcaRepository.findById(id);
        return marca.isPresent() ? marca : null;
    }

    public Page<Marca> findAll(Pageable pageable) {
        return this.marcaRepository.findAllByIsActive(pageable, true);
    }

    public boolean updateStatusById(Long id) {
        if(this.marcaRepository.existsById(id)) {
            Boolean status = this.marcaRepository.getById(id).getIsActive();
            updateStatus(id, !status);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    protected void saveMarca(Marca marca){
        this.marcaRepository.save(marca);
    }

    @Transactional
    protected void updateStatus(Long id, Boolean status){
        this.marcaRepository.updateStatus(id, status);
    }

}
