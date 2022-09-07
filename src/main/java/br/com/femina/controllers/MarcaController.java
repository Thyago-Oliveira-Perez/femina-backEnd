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

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/{idMarca}")
    public ResponseEntity<Marca> findById(@PathVariable("idMarca")Long idMarca) {
        if(this.marcaService.findById(idMarca).isPresent()){
            return ResponseEntity.ok().body(this.marcaService.findById(idMarca).get());
        }else{
            return ResponseEntity.badRequest().body(new Marca());
        }
    }

    @GetMapping
    @Cacheable(value = "marcaFindAll")
    public ResponseEntity<Page<Marca>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.marcaService.findAll(pageable));
    }

    @PostMapping
    @CacheEvict(value = "marcaFindAll")
    public ResponseEntity<?> insert(@RequestBody Marca marca) {
        try{
            this.marcaService.insert(marca);
            return ResponseEntity.ok().body("Marca cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{idMarca}")
    @CacheEvict(value = "marcaFindAll")
    public ResponseEntity<?> updateStatus(@PathVariable("idMarca") Long idMarca)
    {
        try {
            this.marcaService.updateStatus(idMarca);
            return ResponseEntity.ok().body("Marca deletada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Marca não existe no banco.");
        }
    }
}
