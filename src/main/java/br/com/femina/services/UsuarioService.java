package br.com.femina.services;

import br.com.femina.configurations.security.services.TokenService;
import br.com.femina.dto.Usuario.UsuarioRequest;
import br.com.femina.dto.Usuario.UsuarioResponse;
import br.com.femina.entities.Cargos;
import br.com.femina.entities.Usuario;
import br.com.femina.enums.Provider;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    BCryptPasswordEncoder senha = new BCryptPasswordEncoder();

    public ResponseEntity<?> registerUser(UsuarioRequest newUsuario){
        try{
            saveUser(this.UsuarioRequestToUsuarioEntity(newUsuario));
            return ResponseEntity.ok().body("Usuario registrado com sucesso!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Usuario já cadastrado.");
        }
    }

    public ResponseEntity<UsuarioResponse> findById(Long id){
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        return usuario.isPresent() ? ResponseEntity.ok().body(this.UsuarioEntityToUsuarioResponse(usuario.get())) : ResponseEntity.notFound().build();
    }

    public Page<Usuario> findAll(Pageable pageable){
        return this.usuarioRepository.findAllByIsActive(pageable, true);
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

    public ResponseEntity<UsuarioResponse> updateUser(Usuario usuario, Long id){
        if(this.usuarioRepository.existsById(id) && usuario.getId().equals(id)){
            usuario.setSenha(senha.encode(usuario.getSenha()));
            saveUser(usuario);
            return ResponseEntity.ok().body(this.UsuarioEntityToUsuarioResponse(usuario));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> registerBySelf(UsuarioRequest newUsuario){
        try{
            saveUser(this.UsuarioRequestToUsuarioEntity(newUsuario));
            return ResponseEntity.ok().body("Registrado com sucesso!");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Dados inválidos!");
        }
    }

    public ResponseEntity<UsuarioResponse> findByMyId(HttpHeaders headers){
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        assert token != null;
        Long idUser = this.tokenService.getUserId(token.substring(7, token.length()));
        Optional<Usuario> usuario = this.usuarioRepository.findUsuarioById(idUser);
        return usuario.isPresent() ?
                ResponseEntity.ok().body(this.UsuarioEntityToUsuarioResponse(usuario.get())) :
                ResponseEntity.notFound().build();
    }

    //É chamado quando o usuario loga pelo facebook
    public void processOAuth2Login(String userEmail){
        boolean existsUser = this.usuarioRepository.existsUsuarioByEmail(userEmail);
        if(!existsUser){
            Usuario newUser = new Usuario();
            newUser.setLogin(userEmail);
            newUser.setEmail(userEmail);
            newUser.setProvider(Provider.FACEBOOK);
            saveUser(newUser);
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

    //<editor-fold desc="Helpers">
    private UsuarioResponse UsuarioEntityToUsuarioResponse(Usuario usuario){
        return new UsuarioResponse(
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getSexo(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getIsActive()
        );
    }

    private Usuario UsuarioRequestToUsuarioEntity(UsuarioRequest newUsuario){
        List<Cargos> cargos = new ArrayList<Cargos>(){{
            add(new Cargos(br.com.femina.enums.Cargos.USUARIO.toString()));
        }};
        return new Usuario(
            newUsuario.getNome(),
            newUsuario.getLogin(),
            senha.encode(newUsuario.getPassword()),
            newUsuario.getSexo(),
            newUsuario.getEmail(),
            newUsuario.getTelefone(),
            cargos,
            Provider.LOCAL
        );
    }
    //</editor-fold>
}
