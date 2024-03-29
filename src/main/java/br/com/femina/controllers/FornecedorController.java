package br.com.femina.controllers;

import br.com.femina.dto.fornecedor.FornecedorRequest;
import br.com.femina.dto.fornecedor.FornecedorResponse;
import br.com.femina.entities.Fornecedor;
import br.com.femina.services.FornecedorService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000", "http://127.0.0.1:3002" ,"http://localhost:3002"})
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("/{idFornecedor}")
    public ResponseEntity<FornecedorResponse> findById(@PathVariable("idFornecedor") UUID idFornecedor) {
        return this.fornecedorService.findById(idFornecedor);
    }

    @GetMapping
    public ResponseEntity<Page<FornecedorResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.fornecedorService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody FornecedorRequest fornecedor) {
        return this.fornecedorService.insert(fornecedor);
    }

    @PutMapping("/{idFornecedor}")
    public ResponseEntity<FornecedorResponse> update(@PathVariable("idFornecedor") UUID idFornecedor,
                                    @RequestBody Fornecedor fornecedor) {
        return this.fornecedorService.update(idFornecedor, fornecedor);
    }

    @PutMapping("/disable/{idFornecedor}")
    public ResponseEntity<?> updateStatus(@PathVariable("idFornecedor") UUID idFornecedor) {
        return this.fornecedorService.updateStatusById(idFornecedor);
    }
}
