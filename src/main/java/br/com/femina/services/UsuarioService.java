package br.com.femina.services;

import br.com.femina.entities.Perfil;
import br.com.femina.entities.Usuario;
import br.com.femina.entities.enums.Cargos;
import br.com.femina.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void registerUser(Usuario usuario){
        this.usuarioRepository.save(usuario);
    }

    @Transactional
    public void registerByOwn(Usuario usuario){
        Perfil perfil = new Perfil();
        perfil.setPerfilName(Cargos.USUARIO.toString());
        List<Perfil> perfis = new ArrayList<Perfil>();
        perfis.add(perfil);
        usuario.setPerfis(perfis);
        this.usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(Long id){
        return this.usuarioRepository.findById(id);
    }

    public Page<Usuario> findAll(Pageable pageable){
        Page<Usuario> listOfUsers = this.usuarioRepository.findAll(pageable);
        if(listOfUsers.getSize() > 0){
            return listOfUsers;
        }else{
            throw new NotFoundException("Não há usuarios cadastrados.");
        }
    }

    @Transactional
    public void disableUserById(Long id){
        this.usuarioRepository.disable(id);
    }

}
