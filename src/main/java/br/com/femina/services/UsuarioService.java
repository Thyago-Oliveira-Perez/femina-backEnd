package br.com.femina.services;

import br.com.femina.configurations.security.Service.TokenService;
import br.com.femina.entities.Perfil;
import br.com.femina.entities.Usuario;
import br.com.femina.enums.Cargos;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FavoritosRepository favoritosRepository;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> registerUser(Usuario usuario){
        try{
            saveUser(usuario);
            return ResponseEntity.ok().body("Usuario registrado com sucesso!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Usuario já cadastrado.");
        }
    }

    public ResponseEntity<?> registerBySelf(Usuario usuario){
        try{
            Perfil perfil = new Perfil();
            perfil.setPerfilName(Cargos.USUARIO.toString());
            List<Perfil> perfis = new ArrayList<Perfil>();
            perfis.add(perfil);
            usuario.setPerfis(perfis);
            saveUser(usuario);
            return ResponseEntity.ok().body("Registrado com sucesso!");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Dados inválidos!");
        }
    }

    public ResponseEntity<Usuario> findById(Long id){
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        return usuario.isPresent() ? ResponseEntity.ok().body(usuario.get()) : ResponseEntity.notFound().build();
    }

    public Page<Usuario> findAll(Pageable pageable){
        return this.usuarioRepository.findAllByIsActive(pageable, true);
    }

    public ResponseEntity<?> updateUser(Usuario usuario, Long id){
        if(this.usuarioRepository.existsById(id) && usuario.getId().equals(id)){
            saveUser(usuario);
            return ResponseEntity.ok().body("Dados atualizados com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateByOwn(Usuario usuario, Long id){
        if(this.usuarioRepository.existsById(id) && usuario.getId().equals(id)){
            saveUser(usuario);
            return ResponseEntity.ok().body("Dados atualizados com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> findByMyId(HttpHeaders headers){
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        Long idUser = this.tokenService.getUserId(token.substring(7, token.length()));
        Optional<Usuario> usuario = this.usuarioRepository.findUsuarioById(idUser);
        return usuario.isPresent() ? ResponseEntity.ok().body(usuario.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> changeStatusById(Long id){
        if(this.usuarioRepository.existsById(id)){
            String mensagem = "";
            Boolean status = this.usuarioRepository.getById(id).getIsActive();
            changeStatus(id, !status);
            deleteFavoritosRelatedToUser(id);
            if(!status.equals(false)){
                mensagem = "desativado";
            }
            mensagem = "ativado";
            return ResponseEntity.ok().body("Usuario " + mensagem + " com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void changeStatus(Long id, Boolean status){
        this.usuarioRepository.updateStatus(id, status);
    }

    @Transactional
    protected void saveUser(Usuario usuario){
        this.usuarioRepository.save(usuario);
    }

    @Transactional
    protected void deleteFavoritosRelatedToUser(Long id){this.favoritosRepository.deleteFavoritosByUsuarioId(id);}
}
