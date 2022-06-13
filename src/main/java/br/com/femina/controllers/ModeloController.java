package br.com.femina.controllers;

import br.com.femina.entities.Modelo;
import br.com.femina.services.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @GetMapping("/{idModelo}")
    public ResponseEntity<Modelo> findById(@PathVariable("idModelo") Long idModelo){
        return ResponseEntity.ok().body(this.modeloService.findById(idModelo).get());
    }

    @GetMapping
    public ResponseEntity<Page<Modelo>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(this.modeloService.findAll(pageable));
    }

    @PutMapping("{/idModelo}")
    public ResponseEntity<?> update(@PathVariable("idModelo")Long idModelo,
                                    @RequestBody Modelo modelo)
    {
        try{
            this.modeloService.update(idModelo, modelo);
            return ResponseEntity.ok().body("Modelo atualizado com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idModelo}")
    public ResponseEntity<?> delete(@PathVariable("idModelo") Long idModelo){
        try{
            this.modeloService.delete(idModelo);
            return ResponseEntity.ok().body("Modelo atualizado com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
