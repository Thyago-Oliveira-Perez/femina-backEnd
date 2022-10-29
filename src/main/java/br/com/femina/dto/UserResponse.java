package br.com.femina.dto;

import br.com.femina.enums.Sexos;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String nome;
    private String login;
    private Sexos sexo;
    private String email;
    private String telefone;
    private Boolean isActive;

}