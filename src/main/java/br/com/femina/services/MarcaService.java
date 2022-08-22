package br.com.femina.services;

import br.com.femina.entities.Marca;
import br.com.femina.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional
    public void insert(Marca marca) {
        this.marcaRepository.save(marca);
    }

    public Optional<Marca> findById(Long id) {
        return this.marcaRepository.findById(id).isPresent() ? this.marcaRepository.findById(id) : null;
    }

    public Page<Marca> findAll(Pageable pageable) {
        return this.marcaRepository.findAll(pageable);
    }

    @Transactional
    public void updateStatus(Long id) {
        if(this.marcaRepository.findById(id).isPresent()) {
            this.marcaRepository.updateStatus(id);
        } else {
            throw new RuntimeException();
        }
    }

}
