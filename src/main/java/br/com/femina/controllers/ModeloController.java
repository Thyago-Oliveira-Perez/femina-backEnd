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

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @GetMapping("/{idModelo}")
    public ResponseEntity<Modelo> findById(@PathVariable("idModelo") Long idModelo) {

        Optional<Modelo> modelo = this.modeloService.findById(idModelo);

        return modelo.isPresent() ? ResponseEntity.ok().body(modelo.get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Cacheable(value = "modeloFindAll")
    public ResponseEntity<Page<Modelo>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.modeloService.findAll(pageable));
    }

    @PostMapping
    @CacheEvict(value = "modeloFindAll")
    public ResponseEntity<?> insert(@RequestBody Modelo modelo) {
        return this.modeloService.insert(modelo) ? ResponseEntity.ok().body("Modelo cadastrado com sucesso!")
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/disable/{idModelo}")
    @CacheEvict(value = "modeloFindAll")
    public ResponseEntity<?> updateStatus(@PathVariable("idModelo") Long idModelo){
        return this.modeloService.updateStatus(idModelo) ? ResponseEntity.ok().body("Modelo atualizado com sucesso!")
                : ResponseEntity.notFound().build();
    }
}
