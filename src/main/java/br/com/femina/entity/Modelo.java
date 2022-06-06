package br.com.femina.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Nome", schema = "public" )
public class Modelo extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "nome", length = 50, unique = true, nullable = false )
    @NotNull(message = "Nome é obrigatório")
    private String Nome;

}
