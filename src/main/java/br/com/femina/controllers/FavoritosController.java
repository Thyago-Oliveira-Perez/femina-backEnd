package br.com.femina.controllers;

import br.com.femina.entities.Favoritos;
import br.com.femina.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/favoritos")
public class FavoritosController {

    @Autowired
    private FavoritosService favoritosService;

    @GetMapping("/{idFavorito}")
    public ResponseEntity<Favoritos> findById(@PathVariable("idFavorito") Long idFavorito) {
        if(favoritosService.findById(idFavorito).isPresent()){
            return ResponseEntity.ok().body(this.favoritosService.findById(idFavorito).get());
        }else{
            return ResponseEntity.badRequest().body(new Favoritos());
        }
    }

    @GetMapping
    public ResponseEntity<Page<Favoritos>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(favoritosService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestParam(name = "idProduto") Long idProduto,
                                    @RequestParam(name = "idCliente") Long idCliente)
    {
        try {
            this.favoritosService.insert(idProduto, idCliente);
            return ResponseEntity.ok().body("Favoritado com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idFavorito}")
    public ResponseEntity<?> updateStatus(@PathVariable("idFavorito") Long idFavorito)
    {
        try{
            this.favoritosService.delete(idFavorito);
            return ResponseEntity.ok().body("Desfavoritado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Favorito não existe no banco.");
        }    
    }

}
