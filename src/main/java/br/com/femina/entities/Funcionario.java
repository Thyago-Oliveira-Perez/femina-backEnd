package br.com.femina.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "funcionarios", schema = "public")
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
