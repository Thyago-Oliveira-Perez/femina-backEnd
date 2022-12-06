package br.com.femina.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractEntity {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Getter @Setter
    @Column(name="cadastrado")
    private LocalDateTime cadastrado;

    @Getter @Setter
    @Column(name="atualizado")
    private LocalDateTime atualizado;

    @Getter @Setter
    @Column(name="ativo", columnDefinition = "boolean default true")
    private Boolean isActive;

    @PrePersist
    private void defaultValues() {
        this.isActive = true;
        this.cadastrado = LocalDateTime.now();
    }

    @PreUpdate
    private void dataAtualizado() { this.atualizado = LocalDateTime.now(); };

}
