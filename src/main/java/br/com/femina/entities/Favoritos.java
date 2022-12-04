package br.com.femina.entities;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "favoritos", schema = "public")
public class Favoritos {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    public Usuario usuario;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id")
    public Produto produto;

}
