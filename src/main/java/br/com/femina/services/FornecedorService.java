package br.com.femina.services;

import br.com.femina.entities.Fornecedor;
import br.com.femina.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.From;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    public FornecedorRepository fornecedorRepository;

    @Transactional
    public boolean insert(Fornecedor fornecedor) {
        if(!this.fornecedorRepository.existsById(fornecedor.getId())){
            this.fornecedorRepository.save(fornecedor);
            return true;
        }else{
            return false;
        }
    }

    public Optional<Fornecedor> findById(Long id) {
        Optional<Fornecedor> fornecedor = this.fornecedorRepository.findById(id);
        return fornecedor.isPresent() ? fornecedor : Optional.empty();
    }

    public Page<Fornecedor> findAll(Pageable pageable) {
        return this.fornecedorRepository.findAllByIsActive(pageable, true);
    }

    @Transactional
    public boolean update(Long id, Fornecedor fornecedor) {
        if (this.fornecedorRepository.existsById(id) && fornecedor.getId().equals(id)){
            this.fornecedorRepository.save(fornecedor);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean updateStatus(Long id) {
        if (this.fornecedorRepository.existsById(id)) {
            Boolean status = this.fornecedorRepository.getById(id).getIsActive();
            this.fornecedorRepository.updateStatus(id, !status);
            return true;
        } else {
            return false;
        }
    }
}
