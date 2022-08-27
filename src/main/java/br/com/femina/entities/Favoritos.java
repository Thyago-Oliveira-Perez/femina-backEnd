package br.com.femina.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name= "favoritos", schema = "public")
public class Favoritos extends AbstractEntity {

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    public Usuario usuario;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "produto_id")
    public Produto produto;

}
