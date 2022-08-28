package br.com.femina.entities;

import br.com.femina.entities.enums.Sexo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario extends AbstractEntity implements UserDetails{

    @Getter @Setter
    @Column(name = "name", nullable = false, length = 30, unique = true)
    @NotNull(message = "Nome de usuário é obrigatório")
    private String nome;

    @Getter @Setter
    @Column(name = "login", nullable = false, length = 30, unique = true)
    @NotNull(message = "Login de usuário é obrigatório")
    private String login;

    @Getter @Setter
    @Column(name = "password", nullable = false, length = 30, unique = true)
    @NotNull(message = "Senha de usuário é obrigatório")
    private String senha;

    @Getter @Setter
    @Column(name = "sexo", nullable = false, length = 15, unique = true)
    @NotNull(message = "Sexo do usuário é obrigatório")
    private Sexo sexo;

    @Getter @Setter
    @Column(name = "email", nullable = false, length = 30, unique = true)
    @NotNull(message = "Email de usuário é obrigatório")
    private String email;

    @Getter @Setter
    @Column(name = "telefone", nullable = false, length = 30, unique = true)
    @NotNull(message = "Telefone do usuário é obrigatório")
    @Pattern(regexp = "([0-9]{20})")
    private String telefone;

    @Getter @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfis = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
