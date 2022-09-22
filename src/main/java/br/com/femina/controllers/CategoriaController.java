package br.com.femina.controllers;

import br.com.femina.entities.Categorias;
import br.com.femina.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Categorias> findById(@PathVariable("idCategoria") Long idCategoria) {
        return this.categoriaService.findById(idCategoria);
    }

    @GetMapping
    @Cacheable(value = "categoriasFindAll")
    public ResponseEntity<Page<Categorias>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.categoriaService.findAll(pageable));
    }

    @PostMapping
    @CacheEvict(value = "categoriasFindAll")
    public ResponseEntity<?> insert(@RequestBody @Valid Categorias categorias) {
        return this.categoriaService.insert(categorias);
    }

    @PutMapping("/disable/{idCategoria}")
    @CacheEvict(value = "categoriasFindAll")
    public ResponseEntity<?> updateStatus(@PathVariable("idCategoria") Long idCategoria){
        return this.categoriaService.updateStatusById(idCategoria);
    }
}
