package br.com.femina.configurations.security.services;

import br.com.femina.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${femina.jwt.expiration}")
    private String expirationDate;

    @Value("${femina.jwt.secret}")
    private String secret;

    public String createToken(Authentication authentication){

        Usuario user = (Usuario)authentication.getPrincipal();

        Date validFrom = new Date();
        Date validUntil = new Date(validFrom.getTime() + Long.parseLong(expirationDate));

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

    public Long getUserId(String token){
        Claims claim =  Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claim.getSubject());
    }
}
