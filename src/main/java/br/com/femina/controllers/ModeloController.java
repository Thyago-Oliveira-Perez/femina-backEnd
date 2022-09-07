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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @GetMapping("/{idModelo}")
    public ResponseEntity<Modelo> findById(@PathVariable("idModelo") Long idModelo) {
        if(this.modeloService.findById(idModelo).isPresent()){
            return ResponseEntity.ok().body(this.modeloService.findById(idModelo).get());
        }else{
            return ResponseEntity.badRequest().body(new Modelo());
        }
    }

    @GetMapping
    @Cacheable(value = "modeloFindAll")
    public ResponseEntity<Page<Modelo>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.modeloService.findAll(pageable));
    }

    @PostMapping
    @CacheEvict(value = "modeloFindAll")
    public ResponseEntity<?> insert(@RequestBody Modelo modelo) {
        try {
            this.modeloService.insert(modelo);
            return ResponseEntity.ok().body("Modelo cadastrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{idModelo}")
    @CacheEvict(value = "modeloFindAll")
    public ResponseEntity<?> updateStatus(@PathVariable("idModelo") Long idModelo)
    {
        try{
            this.modeloService.updateStatus(idModelo);
            return ResponseEntity.ok().body("Modelo atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Modelo não existe no banco.");
        }
    }
}
