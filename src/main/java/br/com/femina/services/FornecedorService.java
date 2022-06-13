package br.com.femina.services;

import br.com.femina.entities.Fornecedor;
import br.com.femina.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Optional;

public class FornecedorService {

    @Autowired
    public FornecedorRepository fornecedorRepository;

    @Transactional
    public void insert(Fornecedor fornecedor) {
        this.fornecedorRepository.save(fornecedor);
    }

    public Page<Fornecedor> listAll (Pageable pageable) {
        return this.fornecedorRepository.findAll(pageable);
    }

    public Optional<Fornecedor> findById(Long id) {
        return this.fornecedorRepository.findById(id);
    }

    public void update(Long id, Fornecedor fornecedor) {
        if (id == fornecedor.getId()){
            this.fornecedorRepository.save(fornecedor);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void updateStatus(Long id, Fornecedor fornecedor) {
        if (id == fornecedor.getId()) {
            this.fornecedorRepository.updateStatus(fornecedor.isHabilitado(), fornecedor.getId());
        } else {
            throw new RuntimeException();
        }
    }
}
