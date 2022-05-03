package br.com.femina.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(schema = "public", name = "funcionarios")
public class Funcionario extends Usuario {

    @Getter
    @Setter
    @Column(name = "salario", nullable = false)
    private BigDecimal salario;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "cargo", nullable = false)
    private Cargo cargo;



}
