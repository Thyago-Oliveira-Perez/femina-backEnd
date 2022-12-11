package br.com.femina.controllers;

import br.com.femina.dto.modelo.ModeloResponse;
import br.com.femina.entities.Modelo;
import br.com.femina.services.ModeloService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000", "http://127.0.0.1:3002" ,"http://localhost:3002"})
@RequestMapping("/api/modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @GetMapping("/{idModelo}")
    public ResponseEntity<Modelo> findById(@PathVariable("idModelo") UUID idModelo) {
        return this.modeloService.findById(idModelo);
    }

    @GetMapping
    public ResponseEntity<Page<ModeloResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.modeloService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Modelo modelo) {
        return this.modeloService.insert(modelo);
    }

    @PutMapping("/edit/{idModelo}")
    public ResponseEntity<?> update(@RequestBody Modelo modelo, @PathVariable("idModelo") UUID idModelo){
        return this.modeloService.update(idModelo, modelo);
    }

    @PutMapping("/disable/{idModelo}")
    public ResponseEntity<?> updateStatus(@PathVariable("idModelo") UUID idModelo){
        return this.modeloService.updateStatusById(idModelo);
    }
}
