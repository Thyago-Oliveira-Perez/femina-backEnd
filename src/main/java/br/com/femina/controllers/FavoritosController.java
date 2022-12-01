package br.com.femina.controllers;

import br.com.femina.dto.usuario.FavoritoDTO;
import br.com.femina.entities.Favoritos;
import br.com.femina.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000"})
@RequestMapping("/api/favoritos")
public class FavoritosController {

    @Autowired
    private FavoritosService favoritosService;

    @GetMapping
    public ResponseEntity<Page<Favoritos>> findUserFavoritos(@RequestHeader HttpHeaders headers, Pageable pageable) {
        return ResponseEntity.ok().body(favoritosService.findUserFavoritos(headers, pageable));
    }

    @PostMapping
    public ResponseEntity<?> handleFavoritos(@RequestBody FavoritoDTO favorito){
        return this.favoritosService.handleFavoritos(favorito);
    }
}
