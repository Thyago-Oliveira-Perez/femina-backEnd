package br.com.femina.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name= "favoritos", schema = "public")
public class Favoritos extends AbstractEntity{

    @Getter
    @Setter
    @JoinColumn(name = "id_cliente")
    @ManyToOne(fetch = FetchType.EAGER)
    public Cliente cliente;

    @Getter
    @Setter
    @JoinColumn(name = "id_produto")
    @ManyToOne(fetch = FetchType.EAGER)
    public Produto produto;

}
