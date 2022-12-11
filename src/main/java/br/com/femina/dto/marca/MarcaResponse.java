package br.com.femina.dto.marca;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class MarcaResponse {
    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private Boolean isActive;
    @Getter @Setter
    private String nome;

    public MarcaResponse(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
