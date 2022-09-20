package br.com.femina.controllers;

import br.com.femina.dto.FavoritoPost;
import br.com.femina.entities.Favoritos;
import br.com.femina.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritosController {

    @Autowired
    private FavoritosService favoritosService;

    @GetMapping
    public ResponseEntity<Page<Favoritos>> findUserFavoritos(@RequestHeader HttpHeaders headers, Pageable pageable) {
        return ResponseEntity.ok().body(favoritosService.findUserFavoritos(headers, pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody FavoritoPost newFavorito){

        return this.favoritosService.insert(newFavorito.getIdProduto(), newFavorito.getIdUser())
                ? ResponseEntity.ok().body("Favoritado adicionado com sucesso!") : ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/{idFavorito}")
    public ResponseEntity<?> delete(@PathVariable("idFavorito") Long idFavorito){
            return this.favoritosService.deleteById(idFavorito)
                    ? ResponseEntity.ok().body("Produtod desfavoritado!") : ResponseEntity.notFound().build();
    }

}
