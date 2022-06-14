package br.com.femina.controllers;

import br.com.femina.entities.Categorias;
import br.com.femina.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Categorias> findById(@PathVariable("idCategoria") Long idCategoria){
        return ResponseEntity.ok().body(this.categoriaService.findById(idCategoria).get());
    }

    @GetMapping
    public ResponseEntity<Page<Categorias>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(this.categoriaService.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<?> insert(@RequestBody Categorias categorias){
        try {
            this.categoriaService.insert(categorias);
            return ResponseEntity.ok().body("Categoria cadastrada com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> delete(@PathVariable("idCategoria") Long idCategoria,
                                    @RequestBody Categorias categorias){
        try {
            this.categoriaService.delete(idCategoria, categorias);
            return ResponseEntity.ok().body("Categoria deletada com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
