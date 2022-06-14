package br.com.femina.controllers;

import br.com.femina.entities.Cor;
import br.com.femina.services.CorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/cores")
public class CorController {

    @Autowired
    private CorService corService;

    @GetMapping("/{idCor}")
    public ResponseEntity<Cor> findById(@PathVariable("idCor")Long idCor) {
        return ResponseEntity.ok().body(this.corService.findById(idCor).get());
    }

    @GetMapping
    public ResponseEntity<Page<Cor>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.corService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody Cor cor) {
        try {
            this.corService.insert(cor);
            return ResponseEntity.ok().body("Cor cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idCor}")
    public ResponseEntity<?> delete(@PathVariable("idCor") Long idCor,
                                    @RequestBody Cor cor)
    {
        try {
            this.corService.delete(idCor, cor);
            return ResponseEntity.ok().body("Cor deletada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
