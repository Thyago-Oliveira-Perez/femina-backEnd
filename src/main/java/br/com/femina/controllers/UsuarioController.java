package br.com.femina.controllers;

import br.com.femina.dto.usuario.UsuarioRequest;
import br.com.femina.dto.usuario.UsuarioResponse;
import br.com.femina.entities.Usuario;
import br.com.femina.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:3002", "http://localhost:3000"})
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //<editor-fold desc="Endpoints para controle de usuarios">
    @PostMapping("/new-user")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioRequest newUsuario){
        return this.usuarioService.registerUser(newUsuario);
    }

    @PostMapping("/update-user/{id}")
    public ResponseEntity<UsuarioResponse> updateUser(@RequestBody Usuario usuario, @PathVariable("id") Long idUsuario){
        return this.usuarioService.updateUser(usuario, idUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable("id")Long id){
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
    //</editor-fold>

    //<editor-fold desc="Endpoints para que usuario controle seus dados">
    @PostMapping("/register")
    public ResponseEntity<?> registerBySelf(@RequestBody UsuarioRequest newUser){
        return this.usuarioService.registerBySelf(newUser);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UsuarioResponse> updateBySelf(@RequestBody Usuario usuario, @PathVariable("id") Long idUsuario){
        return this.usuarioService.updateUser(usuario, idUsuario);
    }

    @GetMapping("/my-infos")
    public ResponseEntity<UsuarioResponse> findByMyId(@RequestHeader HttpHeaders headers){
        return this.usuarioService.findByMyId(headers);
    }
    //</editor-fold>
}
