package br.com.femina.configurations.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

public class TokenRefreshRequest {

    @Getter @Setter
    private String refreshToken;
}
