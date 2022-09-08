package br.com.femina.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "marcas", schema = "public")
public class Marca extends AbstractEntity{

    @Getter @Setter
    @Column(name = "nome",length = 30, unique = true, nullable = false)
    @NotNull(message = "Nome é obrigatório")
    private String nome;

}
