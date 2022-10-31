package br.com.femina.dto.usuario;

import br.com.femina.enums.Sexos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String login;
    @Getter @Setter
    private Sexos sexo;
    @Getter @Setter
    private String telefone;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
}
