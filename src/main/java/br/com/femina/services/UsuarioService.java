package br.com.femina.services;

import br.com.femina.entities.Perfil;
import br.com.femina.entities.Usuario;
import br.com.femina.entities.enums.Cargos;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FavoritosRepository favoritosRepository;

    public boolean registerUser(Usuario usuario){
        if(!this.usuarioRepository.existsById(usuario.getId())){
            saveUser(usuario);
            return true;
        }else{
            return false;
        }
    }

    public boolean registerBySelf(Usuario usuario){
        if(!this.usuarioRepository.existsById(usuario.getId())){
            Perfil perfil = new Perfil();
            perfil.setPerfilName(Cargos.USUARIO.toString());
            List<Perfil> perfis = new ArrayList<Perfil>();
            perfis.add(perfil);
            usuario.setPerfis(perfis);
            saveUser(usuario);
            return true;
        }else{
            return false;
        }
    }

    public Optional<Usuario> findById(Long id){
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        return usuario.isPresent() ? usuario : Optional.empty();
    }

    public Page<Usuario> findAll(Pageable pageable){
        return this.usuarioRepository.findAllByIsActive(pageable, true);
    }

    public boolean updateUser(Usuario usuario, Long id){
        if(this.usuarioRepository.existsById(id) && usuario.getId().equals(id)){
            saveUser(usuario);
            return true;
        }else{
            return false;
        }
    }

    public boolean updateByOwn(Usuario usuario, Long id){
        if(this.usuarioRepository.existsById(id) && usuario.getId().equals(id)){
            saveUser(usuario);
            return true;
        }else{
            return false;
        }
    }

    public boolean changeStatusById(Long id){
        if(this.usuarioRepository.existsById(id)){
            Boolean status = this.usuarioRepository.getById(id).getIsActive();
            changeStatus(id, !status);
            deleteFavoritosRelatedToUser(id);
            return true;
        }else{
            return false;
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
