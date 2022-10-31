package br.com.femina.dto.Usuario;

import br.com.femina.enums.Sexos;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private String nome;
    private String login;
    private Sexos sexo;
    private String telefone;
    private String email;
    private Boolean isActive;
}