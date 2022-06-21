package br.com.femina.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes", schema = "public")
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente")
    private Set<Favoritos> favoritos;

}
