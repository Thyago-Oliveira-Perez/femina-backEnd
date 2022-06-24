package br.com.femina.controllers;

import br.com.femina.entities.Cliente;
import br.com.femina.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> findById(@PathVariable("idCliente") Long idCliente) {
        return ResponseEntity.ok().body(this.clienteService.findById(idCliente).get());
    }

    @GetMapping
    public ResponseEntity<Page<Cliente>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.clienteService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody Cliente cliente) {
        try{
            this.clienteService.insert(cliente);
            return ResponseEntity.ok().body("Cliente cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,
                                    @PathVariable Long idCliente)
    {
        try{
            this.clienteService.update(idCliente,cliente);
            return ResponseEntity.ok().body("Cliente atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> delete(@RequestBody Cliente cliente,
                                    @PathVariable Long idCliente)
    {
        try{
            this.clienteService.delete(idCliente, cliente);
            return ResponseEntity.ok().body("Cliente deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
