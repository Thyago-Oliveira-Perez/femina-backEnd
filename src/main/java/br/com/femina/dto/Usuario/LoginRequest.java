package br.com.femina.dto.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull
    @Getter @Setter
    private String login;
    @NotNull
    @Getter @Setter
    private String password;
}
