package br.com.femina.configurations.security.services;

import br.com.femina.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${femina.jwt.tokenExpiration}")
    private Long expirationDate;

    @Value("${femina.jwt.secret}")
    private String secret;

    public String generateToken(Usuario user){

        Date validFrom = new Date();
        Date validUntil = new Date(validFrom.getTime() + expirationDate);

        return Jwts.builder()
                .setIssuer("API Femina")
                .setSubject(user.getId().toString())
                .setIssuedAt(validFrom)
                .setExpiration(validUntil)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValid(String token){

        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public UUID getUserId(String token){
        Claims claim =  Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return UUID.fromString(claim.getSubject());
    }

    public String getUserName(Authentication authentication) {
        Usuario usuario = (Usuario)authentication.getPrincipal();
        return usuario.getNome();
    }
}
