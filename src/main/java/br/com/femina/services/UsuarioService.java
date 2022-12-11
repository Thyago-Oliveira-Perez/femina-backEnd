package br.com.femina.services;

import br.com.femina.configurations.security.services.TokenService;
import br.com.femina.dto.ImageRequest;
import br.com.femina.dto.usuario.UsuarioEditRequest;
import br.com.femina.dto.usuario.UsuarioRequest;
import br.com.femina.dto.usuario.UsuarioResponse;
import br.com.femina.entities.Cargos;
import br.com.femina.entities.Usuario;
import br.com.femina.enums.Enums;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.PerfilRepository;
import br.com.femina.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.DataInput;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private FavoritosRepository favoritosRepository;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private PerfilRepository perfilRepository;

    BCryptPasswordEncoder senha = new BCryptPasswordEncoder();

    public ResponseEntity<?> registerUser(UsuarioRequest newUsuario){
        try{
            saveUser(this.usuarioRequestToDbUsuario(newUsuario));
            return ResponseEntity.ok().body("Usuario registrado com sucesso!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Usuario já cadastrado.");
        }
    }

    public ResponseEntity<UsuarioResponse> findById(UUID id){
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        return usuario.isPresent() ? ResponseEntity.ok().body(this.dbUsuarioToUsuarioResponse(usuario.get())) : ResponseEntity.notFound().build();
    }

    public Page<Usuario> findAll(Pageable pageable){
        return this.usuarioRepository.findAllByIsActive(pageable, true);
    }

    public ResponseEntity<?> changeStatusById(UUID id){
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

    public ResponseEntity<UsuarioResponse> updateUser(Usuario usuario, UUID id){
        if(this.usuarioRepository.existsById(id) && usuario.getId().equals(id)){
            usuario.setSenha(senha.encode(usuario.getSenha()));
            saveUser(usuario);
            return ResponseEntity.ok().body(this.dbUsuarioToUsuarioResponse(usuario));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<UsuarioResponse> updateMySelf (UsuarioEditRequest usuarioRequest, UUID id){
        if(this.usuarioRepository.existsById(id) && usuarioRequest.getId().equals(id)){
            try {
                Usuario oldUsuario = usuarioRepository.getById(id);
                Usuario newUsuario = dbUsuarioEditRequestToUsuario(usuarioRequest);
                oldUsuario.setLogin(newUsuario.getLogin());
                oldUsuario.setNome(newUsuario.getNome());
                oldUsuario.setEmail(newUsuario.getEmail());
                oldUsuario.setSexo(newUsuario.getSexo());
                oldUsuario.setTelefone(newUsuario.getTelefone());
                saveUser(oldUsuario);
                return ResponseEntity.ok().body(this.dbUsuarioToUsuarioResponse(oldUsuario));
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> registerBySelf(UsuarioRequest newUsuario){
        try{
            saveUser(this.usuarioRequestToDbUsuario(newUsuario));
            return ResponseEntity.ok().body("Registrado com sucesso!");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Dados inválidos!");
        }
    }

    public ResponseEntity<UsuarioResponse> findByMyId(HttpHeaders headers){
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        assert token != null;
        UUID idUser = this.tokenService.getUserId(token.substring(7, token.length()));
        Optional<Usuario> usuario = this.usuarioRepository.findUsuarioById(idUser);
        return usuario.isPresent() ?
                ResponseEntity.ok().body(this.dbUsuarioToUsuarioResponse(usuario.get())) :
                ResponseEntity.notFound().build();
    }

    //É chamado quando o usuario loga pelo facebook
    public void processOAuth2Login(String userEmail){
        boolean existsUser = this.usuarioRepository.existsUsuarioByEmail(userEmail);
        if(!existsUser){
            Usuario newUser = new Usuario();
            newUser.setLogin(userEmail);
            newUser.setEmail(userEmail);
            newUser.setProvider(Enums.Provider.FACEBOOK);
            saveUser(newUser);
        }
    }

    @Transactional
    protected void changeStatus(UUID id, Boolean status){
        this.usuarioRepository.updateStatus(id, status);
    }

    @Transactional
    protected void saveUser(Usuario usuario){
        this.usuarioRepository.save(usuario);
    }

    @Transactional
    protected void deleteFavoritosRelatedToUser(UUID id){this.favoritosRepository.deleteFavoritosByUsuarioId(id);}

    //<editor-fold desc="Helpers">
    private UsuarioResponse dbUsuarioToUsuarioResponse(Usuario usuario){
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getSexo(),
                usuario.getTelefone(),
                usuario.getEmail(),
                usuario.getIsActive()
        );
    }

    private Usuario usuarioRequestToDbUsuario(UsuarioRequest newUsuario){
        List<Cargos> cargos = perfilRepository.findCargosByCargoName(Enums.Cargos.USUARIO.toString());
        return new Usuario(
            newUsuario.getNome(),
            newUsuario.getLogin(),
            senha.encode(newUsuario.getPassword()),
            newUsuario.getSexo(),
            newUsuario.getEmail(),
            newUsuario.getTelefone(),
            cargos,
            Enums.Provider.LOCAL
        );
    }

    private Usuario dbUsuarioEditRequestToUsuario(UsuarioEditRequest usuario){
        return new Usuario(
                usuario.getNome(),
                usuario.getLogin(),
                null,
                usuario.getSexo(),
                usuario.getEmail(),
                usuario.getTelefone(),
                null,
                Enums.Provider.LOCAL
        );
    }
    //</editor-fold>
}
