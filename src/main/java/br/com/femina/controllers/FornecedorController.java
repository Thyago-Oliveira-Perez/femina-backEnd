package br.com.femina.controllers;

import br.com.femina.entities.Fornecedor;
import br.com.femina.services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("/{idFornecedor}")
    public ResponseEntity<Fornecedor> findById(@PathVariable("idFornecedor") Long idFornecedor) {
        if(this.fornecedorService.findById(idFornecedor).isPresent()){
            return ResponseEntity.ok().body(this.fornecedorService.findById(idFornecedor).get());
        }else{
            return ResponseEntity.badRequest().body(new Fornecedor());
        }
    }

    @GetMapping
    @Cacheable(value = "fornecedoresFindAll")
    public ResponseEntity<Page<Fornecedor>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.fornecedorService.findAll(pageable));
    }

    @PostMapping
    @CacheEvict(value = "fornecedoresFindAll")
    public ResponseEntity<?> insert(@RequestBody Fornecedor fornecedor) {
        try{
            this.fornecedorService.insert(fornecedor);
            return ResponseEntity.ok().body("Fornecedor cadastrada com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idFornecedor}")
    @CacheEvict(value = "fornecedoresFindAll")
    public ResponseEntity<?> update(@PathVariable("idFornecedor") Long idFornecedor,
                                    @RequestBody Fornecedor fornecedor)
    {
        try {
            this.fornecedorService.update(idFornecedor, fornecedor);
            return ResponseEntity.ok().body("Fornecedor editado com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{idFornecedor}")
    @CacheEvict(value = "fornecedoresFindAll")
    public ResponseEntity<?> updateStatus(@PathVariable("idFornecedor") Long idFornecedor)
    {
        try {
            this.fornecedorService.updateStatus(idFornecedor);
            return ResponseEntity.ok().body("Fornecedor deletado com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Fornecedor não existe no banco.");
        }
    }
}
