package br.com.femina.configurations.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class JwtResponse {

    @Getter @Setter
    public String token;
    public String authenticationType = "Bearer";
    @Getter @Setter
    public String refreshToken;

    public JwtResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
