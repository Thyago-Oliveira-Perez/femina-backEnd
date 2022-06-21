package br.com.femina.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
    public Cliente cliente;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "produto_id")
    public Produto produto;

}
