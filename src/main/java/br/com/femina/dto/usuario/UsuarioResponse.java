package br.com.femina.dto.usuario;

import br.com.femina.enums.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String login;
    @Getter @Setter
    private Enums.Sexos sexo;
    @Getter @Setter
    private String telefone;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private Boolean isActive;
}