package br.com.femina.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    //Configurações de autenticações
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.autenticacaoService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configurações de autorização(urls publicas e as que precisam de controller)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/produtos").permitAll()
                .antMatchers(HttpMethod.GET, "/api/produtos/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/usuarios/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("login.tsx")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/home.tsx", true)
                .failureUrl("/login.tsx?error=true")
                .and()
                .logout()
                .logoutUrl("/perform_logout");
    }

    //Configurações de recursos estaticos(js, css, html, imagens, etc...)
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }
}
