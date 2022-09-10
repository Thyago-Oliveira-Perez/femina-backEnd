package br.com.femina.controllers;

import br.com.femina.entities.Marca;
import br.com.femina.services.MarcaService;
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
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/{idMarca}")
    public ResponseEntity<Marca> findById(@PathVariable("idMarca")Long idMarca) {

        Optional<Marca> marca = this.marcaService.findById(idMarca);

        return marca.isPresent() ? ResponseEntity.ok().body(marca.get())
                : ResponseEntity.notFound().build();

    }

    @GetMapping
    @Cacheable(value = "marcaFindAll")
    public ResponseEntity<Page<Marca>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.marcaService.findAll(pageable));
    }

    @PostMapping
    @CacheEvict(value = "marcaFindAll")
    public ResponseEntity<?> insert(@RequestBody Marca marca) {
        return this.marcaService.insert(marca) ? ResponseEntity.ok().body("Marca cadastrada com sucesso!")
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/disable/{idMarca}")
    @CacheEvict(value = "marcaFindAll")
    public ResponseEntity<?> updateStatus(@PathVariable("idMarca") Long idMarca) {
        return this.marcaService.updateStatus(idMarca) ? ResponseEntity.ok().body("Marca deletada com sucesso!")
                : ResponseEntity.notFound().build();
    }
}
