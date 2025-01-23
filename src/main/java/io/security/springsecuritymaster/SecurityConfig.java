package io.security.springsecuritymaster;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/{name}")
                        .access(new WebExpressionAuthorizationManager("#name == authentication.name"))
                        .requestMatchers("/admin/db")
                        .access(new WebExpressionAuthorizationManager("hasAuthority('ROLE_DB') or hasAuthority('ROLE_ADMIN')"))
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults()) // 인증을 받지 못했을 경우에 formLogin 방식(default)으로 인증을 받는다.
                ;
        return http.build();
    }*/

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApplicationContext context) throws Exception {

        DefaultHttpSecurityExpressionHandler expressionHandler = new DefaultHttpSecurityExpressionHandler();
        expressionHandler.setApplicationContext(context);

        WebExpressionAuthorizationManager authorizationManager
                = new WebExpressionAuthorizationManager("@customWebSecurity.check(authentication, request)");
        authorizationManager.setExpressionHandler(expressionHandler);

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/custom/**").access(authorizationManager)
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults()) // 인증을 받지 못했을 경우에 formLogin 방식(default)으로 인증을 받는다.
        ;
        return http.build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApplicationContext context) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new CustomRequestMatcher("/admin")).hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults()) // 인증을 받지 못했을 경우에 formLogin 방식(default)으로 인증을 받는다.
        ;
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() { // 설정 파일(yml)보다 우선시 됨.
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        UserDetails manager = User.withUsername("manager").password("{noop}1111").roles("MANAGER").build();
        UserDetails admin = User.withUsername("admin").password("{noop}1111").roles("ADMIN", "WRITE").build();
        return new InMemoryUserDetailsManager(user, manager, admin);

    }
}
