package br.com.femina.entities;

import br.com.femina.enums.Provider;
import br.com.femina.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "usuarios", schema = "public")
public class Usuario extends AbstractEntity implements UserDetails{

    @Getter @Setter
    @Column(name = "name", nullable = false, length = 30, unique = true)
    private String nome;

    @Getter @Setter
    @Column(name = "login", nullable = false, length = 30, unique = true)
    private String login;

    @Getter @Setter
    @Column(name = "password", nullable = false, length = 255, unique = true)
    private String senha;

    @Getter @Setter
    @Column(name = "sexo", nullable = false, length = 15, unique = true)
    private Sexo sexo;

    @Getter @Setter
    @Column(name = "email", nullable = false, length = 30, unique = true)
    private String email;

    @Getter @Setter
    @Column(name = "telefone", nullable = false, length = 30, unique = true)
    private String telefone;

    @Getter @Setter
    @ManyToMany
    @JoinTable(name = "usuarios_perfis",
            joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_perfil", referencedColumnName = "id"))
    private Collection<Perfil> perfis = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private Provider provider;

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
