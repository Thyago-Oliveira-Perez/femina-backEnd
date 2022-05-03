package br.com.femina.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public" , name = "cor")
public class Cor extends AbstractEntity  {
//nome hexadecimal
    @Getter
    @Setter
    @Column(name = "nome", nullable = false, length = 50, unique = true)
    private String nome;

    @Getter
    @Setter
    @Column(name = "hexadecimal", nullable = false, length = 50)
    private String hexadecimal;

}
