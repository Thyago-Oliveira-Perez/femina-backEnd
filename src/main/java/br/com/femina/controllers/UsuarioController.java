package br.com.femina.controllers;

import br.com.femina.entities.Usuario;
import br.com.femina.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/new-user")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario){
        try {
            this.usuarioService.registerUser(usuario);
            return ResponseEntity.ok().body("Usuario cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> newUser(@RequestBody Usuario usuario){
        try {
            this.usuarioService.registerByOwn(usuario);
            return ResponseEntity.ok().body("Cadastrado realizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable("id")Long id){
        Optional<Usuario> usuario = this.usuarioService.findById(id);
        return usuario.isPresent() ? ResponseEntity.ok().body(usuario.get()) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/list-all")
    public ResponseEntity<Page<Usuario>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(this.usuarioService.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> disableUserById(@PathVariable("id") Long id){
        try{
            this.usuarioService.disableUserById(id);
            return ResponseEntity.ok().body("Usuario desabilitado com sucesso.");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("");
        }
    }
}
