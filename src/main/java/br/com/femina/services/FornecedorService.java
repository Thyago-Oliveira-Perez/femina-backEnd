package br.com.femina.services;

import br.com.femina.entities.Fornecedor;
import br.com.femina.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    public FornecedorRepository fornecedorRepository;

    public ResponseEntity<?> insert(Fornecedor fornecedor) {
        try{
            saveFornecedor(fornecedor);
            return ResponseEntity.ok().body("Fornecedor cadastrada com sucesso!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fornecedor já cadastrado!");
        }
    }

    public ResponseEntity<Fornecedor> findById(Long id) {
        Optional<Fornecedor> fornecedor = this.fornecedorRepository.findById(id);
        return fornecedor.isPresent() ? ResponseEntity.ok().body(fornecedor.get()) : ResponseEntity.notFound().build();
    }

    public Page<Fornecedor> findAll(Pageable pageable) {
        return this.fornecedorRepository.findAllByIsActive(pageable, true);
    }

    public ResponseEntity<?> update(Long id, Fornecedor fornecedor) {
        if (this.fornecedorRepository.existsById(id) && fornecedor.getId().equals(id)){
            saveFornecedor(fornecedor);
            return ResponseEntity.ok().body("Fornecedor atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateStatusById(Long id) {
        String mensagem = "";
        if (this.fornecedorRepository.existsById(id)) {
            Boolean status = this.fornecedorRepository.getById(id).getIsActive();
            updateStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativada";
            }
            mensagem = "desativada";
            return ResponseEntity.ok().body("Fornecedor " + mensagem + " com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
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
