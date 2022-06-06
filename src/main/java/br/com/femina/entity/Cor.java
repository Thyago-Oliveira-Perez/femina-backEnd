package br.com.femina.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public" , name = "cor")
public class Cor extends AbstractEntity  {
//nome hexadecimal
    @Getter
    @Setter
    @Column(name = "nome", nullable = false, length = 50, unique = true)
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Getter
    @Setter
    @Column(name = "hexadecimal", nullable = false, length = 50)
    @NotNull(message = "Hexadecimal é obrigatório")
    private String hexadecimal;

}
