package br.com.femina.security.Dto;

public class Token {

    public String token;
    //tipo de autenticação = Bearer
    public String AuthenticationType = "Bearer";

    public Token(String token) {
        this.token = token;
    }
}
