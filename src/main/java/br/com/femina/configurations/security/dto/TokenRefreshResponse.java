package br.com.femina.configurations.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TokenRefreshResponse {

    @Getter @Setter
    private String accessToken;
    @Getter @Setter
    private String refreshToken;
    @Getter @Setter
    public String authenticationType = "Bearer";

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
