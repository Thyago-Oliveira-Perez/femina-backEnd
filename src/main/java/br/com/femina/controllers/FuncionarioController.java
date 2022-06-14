package br.com.femina.controllers;

import br.com.femina.entities.Funcionario;
import br.com.femina.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/Funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<Page<Funcionario>> listAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.funcionarioService.findAll(pageable));
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> findById(@PathVariable("idFuncionario") Long idFuncionario) {
        return ResponseEntity.ok().body(this.funcionarioService.findById(idFuncionario).get());
    }

    @PostMapping
    public ResponseEntity<?> insertFuncionario(@RequestBody Funcionario funcionario) {
        try{
            this.funcionarioService.insert(funcionario);
            return ResponseEntity.ok().body("Funcionario cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idFuncionario}")
    public ResponseEntity<?> update(@RequestBody Funcionario funcionario, @PathVariable Long idFuncionario) {
        try{
            this.funcionarioService.update(idFuncionario, funcionario);
            return ResponseEntity.ok().body("Funcionario atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{idFuncionario}")
    public ResponseEntity<?> updateStatus(@RequestBody Funcionario funcionario, @PathVariable Long idFuncionario) {
        try{
            this.funcionarioService.delete(idFuncionario, funcionario);
            return ResponseEntity.ok().body("Funcionario desabilitada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
