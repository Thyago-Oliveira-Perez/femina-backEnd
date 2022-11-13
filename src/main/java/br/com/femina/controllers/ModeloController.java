package br.com.femina.controllers;

import br.com.femina.entities.Modelo;
import br.com.femina.services.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:3002"})
@RequestMapping("/api/modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @GetMapping("/{idModelo}")
    public ResponseEntity<Modelo> findById(@PathVariable("idModelo") Long idModelo) {
        return this.modeloService.findById(idModelo);
    }

    @GetMapping
    @Cacheable(value = "modeloFindAll")
    public ResponseEntity<Page<Modelo>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.modeloService.findAll(pageable));
    }

    @PostMapping
    @CacheEvict(value = "modeloFindAll")
    public ResponseEntity<?> insert(@RequestBody Modelo modelo) {
        return this.modeloService.insert(modelo);
    }

    @PutMapping("/disable/{idModelo}")
    @CacheEvict(value = "modeloFindAll")
    public ResponseEntity<?> updateStatus(@PathVariable("idModelo") Long idModelo){
        return this.modeloService.updateStatusById(idModelo);
    }
}
