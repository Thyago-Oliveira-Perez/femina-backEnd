package br.com.femina.security.Service;

import br.com.femina.security.Repository.AuthRepository;
import br.com.femina.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuario = this.authRepository.findByLoginOrEmail(username);

        if(usuario.isPresent() && usuario.get().isAccountNonExpired()){
            return usuario.get();
        }else{
            return null;
        }
    }
}
