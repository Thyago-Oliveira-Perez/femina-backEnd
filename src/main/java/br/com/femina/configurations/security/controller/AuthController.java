package br.com.femina.configurations.security.controller;

import br.com.femina.configurations.security.dto.TokenRefreshRequest;
import br.com.femina.configurations.security.dto.TokenRefreshResponse;
import br.com.femina.configurations.security.services.RefreshTokenService;
import br.com.femina.dto.usuario.LoginRequest;
import br.com.femina.configurations.security.dto.JwtResponse;
import br.com.femina.configurations.security.services.TokenService;
import br.com.femina.entities.RefreshToken;
import br.com.femina.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000/", "http://localhost:3000/", "http://127.0.0.1:3002/" ,"http://localhost:3002/"})
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest credentials){

        try{

            UsernamePasswordAuthenticationToken loginCredentials =
                    new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword());

            Authentication authentication = authenticationManager.authenticate(loginCredentials);

            Usuario user = (Usuario)authentication.getPrincipal();

            String token = tokenService.generateToken(user);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authentication);

            return ResponseEntity.ok().body(new JwtResponse(token, refreshToken.getToken()));
        }catch (AuthenticationException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponse> refreshtoken(@RequestBody @Valid TokenRefreshRequest request) {

        try{
            return ResponseEntity.ok().body(refreshTokenService.generateTokenFromRefreshToken(request));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
