package br.com.femina.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "categoria", schema = "public")
public class Categorias  extends AbstractEntily {

    @Getter @Setter
    @Column(name = "nome", length = 50, unique = true, nullable = false)
    private String nome;
}
