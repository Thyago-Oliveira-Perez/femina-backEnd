package br.com.femina.controllers;

import br.com.femina.dto.categoria.CategoriaResponse;
import br.com.femina.entities.Categorias;
import br.com.femina.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.UUID;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000", "http://127.0.0.1:3002" ,"http://localhost:3002"})
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponse> findById(@PathVariable("idCategoria") UUID idCategoria) {
        return this.categoriaService.findById(idCategoria);
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.categoriaService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid Categorias categorias) {
        return this.categoriaService.insert(categorias);
    }

    @PutMapping("/disable/{idCategoria}")
    public ResponseEntity<?> updateStatus(@PathVariable("idCategoria") UUID idCategoria){
        return this.categoriaService.updateStatusById(idCategoria);
    }
}
