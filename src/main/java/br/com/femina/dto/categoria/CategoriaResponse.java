package br.com.femina.dto.categoria;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CategoriaResponse {
    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private String nome;

    public CategoriaResponse(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
