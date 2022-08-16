package br.com.femina.services;

import br.com.femina.entities.Fornecedor;
import br.com.femina.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    public FornecedorRepository fornecedorRepository;

    @Transactional
    public void insert(Fornecedor fornecedor) {
        this.fornecedorRepository.save(fornecedor);
    }

    public Optional<Fornecedor> findById(Long id) {
        return this.fornecedorRepository.findById(id);
    }

    public Page<Fornecedor> findAll(Pageable pageable) {
        return this.fornecedorRepository.findAll(pageable);
    }

    @Transactional
    public void update(Long id, Fornecedor fornecedor) {
        if (id == fornecedor.getId()){
            this.fornecedorRepository.save(fornecedor);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void updateStatus(Long id) {
        if (this.fornecedorRepository.findById(id).isPresent()) {
            this.fornecedorRepository.updateStatus(id);
        } else {
            throw new RuntimeException();
        }
    }

}
