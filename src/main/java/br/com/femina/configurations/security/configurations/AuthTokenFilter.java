package br.com.femina.configurations.security.configurations;

import br.com.femina.entities.Usuario;
import br.com.femina.repositories.UsuarioRepository;
import br.com.femina.configurations.security.Service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private String requestHeader = "Authorization";
    /*
     *   esse espaço é necessario, por que na request vai vir assim
     *   "tipoDeAutenticacao token"
     * */
    private String AuthenticationType = "Bearer ";

    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;

    public AuthTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        boolean isTokenValid = tokenService.isTokenValid(token);

        if (isTokenValid) {
            authUser(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authUser(String token) {
        Usuario user = usuarioRepository.findById(tokenService.getUserId(token)).get();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String token = request.getHeader(requestHeader);

        if(token == null || token.isEmpty() || !token.startsWith(AuthenticationType)){
            return null;
        }

        return token.substring(7, token.length());
    }
}
