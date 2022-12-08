package br.com.femina.configurations.security.services;

import br.com.femina.configurations.security.dto.TokenRefreshRequest;
import br.com.femina.configurations.security.dto.TokenRefreshResponse;
import br.com.femina.entities.RefreshToken;
import br.com.femina.entities.Usuario;
import br.com.femina.repositories.RefreshTokenRepository;
import br.com.femina.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Service
public class RefreshTokenService {
    @Value("${femima.jwt.refreshTokeExpiration}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public TokenRefreshResponse generateTokenFromRefreshToken(TokenRefreshRequest request){
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = this.findByToken(requestRefreshToken).get();

        assertFalse(this.tokenIsExpired(refreshToken), "Token has expired!!");

        String newToken = this.tokenService.generateToken(refreshToken.getUser());

        return new TokenRefreshResponse(newToken, requestRefreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public boolean tokenIsExpired(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            return true;
        }

        return false;
    }

    public RefreshToken createRefreshToken(Authentication authentication) {
        Usuario user = (Usuario)authentication.getPrincipal();
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(user.getId()).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public int deleteByUserId(UUID userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
