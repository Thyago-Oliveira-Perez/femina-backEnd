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

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("/{idFornecedor}")
    public ResponseEntity<Fornecedor> findById(@PathVariable("idFornecedor") Long idFornecedor) {

        Optional<Fornecedor> fornecedor = this.fornecedorService.findById(idFornecedor);

        return fornecedor.isPresent() ? ResponseEntity.ok().body(fornecedor.get())
        : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Cacheable(value = "fornecedoresFindAll")
    public ResponseEntity<Page<Fornecedor>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.fornecedorService.findAll(pageable));
    }

    @PostMapping
    @CacheEvict(value = "fornecedoresFindAll")
    public ResponseEntity<?> insert(@RequestBody Fornecedor fornecedor) {
        return this.fornecedorService.insert(fornecedor) ?
                ResponseEntity.ok().body("Fornecedor cadastrada com sucesso!") : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{idFornecedor}")
    @CacheEvict(value = "fornecedoresFindAll")
    public ResponseEntity<?> update(@PathVariable("idFornecedor") Long idFornecedor,
                                    @RequestBody Fornecedor fornecedor) {
            return this.fornecedorService.update(idFornecedor, fornecedor) ?
                    ResponseEntity.ok().body("Fornecedor editado com sucesso!") : ResponseEntity.badRequest().build();
    }

    @PutMapping("/disable/{idFornecedor}")
    @CacheEvict(value = "fornecedoresFindAll")
    public ResponseEntity<?> updateStatus(@PathVariable("idFornecedor") Long idFornecedor) {
        return this.fornecedorService.updateStatus(idFornecedor) ?
                ResponseEntity.ok().body("Fornecedor deletado com sucesso!") : ResponseEntity.notFound().build();
    }
}
