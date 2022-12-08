package br.com.femina.configurations.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class JwtResponse {

    @Getter @Setter
    public String accessToken;
    @Getter @Setter
    public String refreshToken;
    public String authenticationType = "Bearer";

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
