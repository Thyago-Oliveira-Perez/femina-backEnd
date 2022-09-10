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

    @Transactional
    public boolean insert(Marca marca) {
        if(!this.marcaRepository.existsById(marca.getId())){
            this.marcaRepository.save(marca);
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

    @Transactional
    public boolean updateStatus(Long id) {
        if(this.marcaRepository.existsById(id)) {
            this.marcaRepository.updateStatus(id);
            return true;
        } else {
            return false;
        }
    }

}
