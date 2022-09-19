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

    public boolean insert(Fornecedor fornecedor) {
        if(!this.fornecedorRepository.existsById(fornecedor.getId())){
            saveFornecedor(fornecedor);
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

    public boolean update(Long id, Fornecedor fornecedor) {
        if (this.fornecedorRepository.existsById(id) && fornecedor.getId().equals(id)){
            saveFornecedor(fornecedor);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateStatusById(Long id) {
        if (this.fornecedorRepository.existsById(id)) {
            Boolean status = this.fornecedorRepository.getById(id).getIsActive();
            updateStatus(id, !status);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    protected void saveFornecedor(Fornecedor fornecedor){
        this.fornecedorRepository.save(fornecedor);
    }

    @Transactional
    protected void updateStatus(Long id, Boolean status){
        this.fornecedorRepository.updateStatus(id, status);
    }
}
