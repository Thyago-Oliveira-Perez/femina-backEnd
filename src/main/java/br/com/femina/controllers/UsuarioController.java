package br.com.femina.controllers;

import br.com.femina.entities.Usuario;
import br.com.femina.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/new-user")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario){

        return this.usuarioService.registerUser(usuario);
    }

    @PostMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Usuario usuario, @PathVariable("id") Long idUsuario){

        return this.usuarioService.updateUser(usuario, idUsuario);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerBySelf(@RequestBody Usuario usuario){

        return this.usuarioService.registerBySelf(usuario);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateBySelf(@RequestBody Usuario usuario, @PathVariable("id") Long idUsuario){

        return this.usuarioService.updateByOwn(usuario, idUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable("id")Long id){
        return this.usuarioService.findById(id);
    }

    @GetMapping("/list-all")
    public ResponseEntity<Page<Usuario>> findAll(Pageable pageable){

        return ResponseEntity.ok().body(this.usuarioService.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> disableUserById(@PathVariable("id") Long id){

        return this.usuarioService.changeStatusById(id);
    }
}
