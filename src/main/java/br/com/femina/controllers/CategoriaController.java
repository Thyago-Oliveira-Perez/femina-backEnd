package br.com.femina.controllers;

import br.com.femina.entities.Categorias;
import br.com.femina.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Categorias> findById(@PathVariable("idCategoria") Long idCategoria) {
        return ResponseEntity.ok().body(this.categoriaService.findById(idCategoria).get());
    }

    @GetMapping
    public ResponseEntity<Page<Categorias>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.categoriaService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid Categorias categorias) {
        try {
            this.categoriaService.insert(categorias);
            return ResponseEntity.ok().body("Categoria cadastrada com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<?> updateStatus(@PathVariable("idCategoria") Long idCategoria){
        if(this.categoriaService.updateStatus(idCategoria)){
            return ResponseEntity.ok().body("Categoria deletada com sucesso!");
        }else {
            return ResponseEntity.badRequest().body("Categoria não existe no banco!");
        }
    }
}
