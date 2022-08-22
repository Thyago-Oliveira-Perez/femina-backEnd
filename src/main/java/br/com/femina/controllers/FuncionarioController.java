package br.com.femina.controllers;

import br.com.femina.entities.Funcionario;
import br.com.femina.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> findById(@PathVariable("idFuncionario") Long idFuncionario) {
        if(this.funcionarioService.findById(idFuncionario).isPresent()){
            return ResponseEntity.ok().body(this.funcionarioService.findById(idFuncionario).get());
        }else{
            return ResponseEntity.badRequest().body(new Funcionario());
        }
    }

    @GetMapping
    public ResponseEntity<Page<Funcionario>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.funcionarioService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Funcionario funcionario) {
        try{
            this.funcionarioService.insert(funcionario);
            return ResponseEntity.ok().body("Funcionario cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idFuncionario}")
    public ResponseEntity<?> update(@RequestBody Funcionario funcionario,
                                    @PathVariable Long idFuncionario)
    {
        try{
            this.funcionarioService.update(idFuncionario, funcionario);
            return ResponseEntity.ok().body("Funcionario atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{idFuncionario}")
    public ResponseEntity<?> updateStatus(@PathVariable Long idFuncionario)
    {
        try{
            this.funcionarioService.updateStatus(idFuncionario);
            return ResponseEntity.ok().body("Funcionario deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Funcionario não existe no banco.");
        }
    }


}
