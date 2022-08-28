package br.com.femina.configuration.security;

import br.com.femina.entities.Usuario;
import br.com.femina.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuario = this.usuarioRepository.findByLoginOrEmail(username);

        if(usuario.isPresent()){
            return usuario.get();
        }
        else{
            throw new NotFoundException("Dados inválidos");
        }
    }
}
