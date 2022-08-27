package br.com.femina.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    //Configurações de autenticações
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    }

    //Configurações de autorização(urls publicas e as que precisam de controller)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/produtos").permitAll()
                .antMatchers(HttpMethod.GET, "/api/produtos/*").permitAll()
                .anyRequest().authenticated();
//                .and().formLogin();
    }

    //Configurações de recursos estaticos(js, css, html, imagens, etc...)
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }
}
