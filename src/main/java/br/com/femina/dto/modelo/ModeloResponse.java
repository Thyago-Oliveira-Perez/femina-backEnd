package br.com.femina.dto.modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class ModeloResponse {
    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private Boolean isActive;
    @Getter @Setter
    private String nome;

    public ModeloResponse(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
