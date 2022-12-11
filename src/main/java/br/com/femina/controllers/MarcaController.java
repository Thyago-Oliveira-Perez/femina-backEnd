package br.com.femina.controllers;

import br.com.femina.dto.marca.MarcaResponse;
import br.com.femina.entities.Marca;
import br.com.femina.services.MarcaService;

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
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/{idMarca}")
    public ResponseEntity<Marca> findById(@PathVariable("idMarca") UUID idMarca) {
        return this.marcaService.findById(idMarca);
    }

    @GetMapping
    public ResponseEntity<Page<MarcaResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.marcaService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Marca marca) {
        return this.marcaService.insert(marca);
    }

    @PutMapping("/edit/{idMarca}")
    public ResponseEntity<?> update(@RequestBody Marca marca, @PathVariable("idMarca") UUID idMarca) {
        return this.marcaService.update(idMarca, marca);
    }

    @PutMapping("/disable/{idMarca}")
    public ResponseEntity<?> updateStatus(@PathVariable("idMarca") UUID idMarca) {
        return this.marcaService.updateStatusById(idMarca);
    }
}
