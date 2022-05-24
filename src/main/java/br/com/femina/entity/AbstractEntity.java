package br.com.femina.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractEntity {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter @Setter
    @Column(name="cadastrado")
    private LocalDateTime cadastrado;

    @Getter @Setter
    @Column(name="atualizado")
    private LocalDateTime atualizado;

    @Getter @Setter
    @Column(name="habilitado")
    private boolean habilitado;

    @PrePersist
    private void dataCadastro(){this.cadastrado = LocalDateTime.now();};

    @PreUpdate
    private void dataAtualizado(){this.atualizado = LocalDateTime.now();};
}
