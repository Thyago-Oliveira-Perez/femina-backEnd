package br.com.femina.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "categorias", schema = "public")
@EqualsAndHashCode
public class Categorias  extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome", length = 50, unique = true, nullable = false)
    @NotNull(message = "Nome é obrigatório")
    private String nome;

}
