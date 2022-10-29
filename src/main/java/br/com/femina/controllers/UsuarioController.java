package br.com.femina.controllers;

import br.com.femina.dto.UserResponse;
import br.com.femina.entities.Usuario;
import br.com.femina.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserResponse> updateUser(@RequestBody Usuario usuario, @PathVariable("id") Long idUsuario){
        return this.usuarioService.updateUser(usuario, idUsuario);
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

    //endpoints que o usuario controla
    @PostMapping("/register")
    public ResponseEntity<?> registerBySelf(@RequestBody Usuario usuario){
        return this.usuarioService.registerBySelf(usuario);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateBySelf(@RequestBody Usuario usuario, @PathVariable("id") Long idUsuario){
        return this.usuarioService.updateUser(usuario, idUsuario);
    }

    @GetMapping("/my-infos")
    public ResponseEntity<?> findByMyId(@RequestHeader HttpHeaders headers){
        return this.usuarioService.findByMyId(headers);
    }
}
