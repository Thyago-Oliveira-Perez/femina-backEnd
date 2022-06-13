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
    public void insert (Marca marca) {
        this.marcaRepository.save(marca);
    }

    public Page<Marca> listAll(Pageable pageable) {
        return this.marcaRepository.findAll(pageable);
    }

    public Optional<Marca> findById(Long id) {
        return this.marcaRepository.findById(id);
    }

    public void update(Long id, Marca marca) {
        if(id == marca.getId()) {
            this.marcaRepository.save(marca);
        } else {
            throw new RuntimeException("Erro ao atualizar marca");
        }
    }

    @Transactional
    public void updateStatus(Long id, Marca marca) {
        if(id == marca.getId()) {
            this.marcaRepository.updateStatus(LocalDateTime.now(), marca.getId());
        } else {
            throw new RuntimeException("Erro ao excluir marca");
        }
    }
}
