package br.com.femina.configurations.security.AuthController;

import br.com.femina.dto.AuthCredentialRequest;
import br.com.femina.dto.Token;
import br.com.femina.configurations.security.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid AuthCredentialRequest credentials){

        UsernamePasswordAuthenticationToken loginCredentials =
                new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword());

        try{
            Authentication authentication = authenticationManager.authenticate(loginCredentials);
            String token = tokenService.createToken(authentication);
            return ResponseEntity.ok().body(new Token(token));
        }catch (AuthenticationException e){
            return ResponseEntity.notFound().build();
        }
    }
}
