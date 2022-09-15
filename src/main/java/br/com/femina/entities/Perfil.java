package br.com.femina.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "perfil", schema = "public")
public class Perfil extends AbstractEntity implements GrantedAuthority{

    @Getter @Setter
    @Column(name = "perfilName", nullable = false, length = 30, unique = true)
    private String perfilName;

    @Override
    public String getAuthority() {
        return this.perfilName;
    }

}
