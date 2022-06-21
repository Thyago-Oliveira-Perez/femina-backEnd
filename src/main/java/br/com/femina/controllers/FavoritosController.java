package br.com.femina.controllers;

import br.com.femina.entities.Favoritos;
import br.com.femina.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/favoritos")
public class FavoritosController {

    @Autowired
    private FavoritosService favoritosService;

    @PostMapping()
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

}
