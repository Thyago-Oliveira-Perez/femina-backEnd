package br.com.femina.entity;

import lombok.*;
import org.hibernate.engine.internal.AbstractEntityEntry;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Marca", schema = "public")
public class Marca extends AbstractEntity{

    @Getter @Setter
    @Table(name = "nome",length = 50, unique = true, nullable = false)
    private String nome;
}
