package io.security.springsecuritymaster;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) // http 통신에 대한 인가 정책을 설정하겠다. (모든 요청)
//                .formLogin(Customizer.withDefaults()); // 인증을 받지 못했을 경우에 formLogin 방식(default)으로 인증을 받는다.
                .formLogin(form -> form
//                        .loginPage("/loginPage")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/", false) // 내부적으로 handler에서 처리.
                        .failureUrl("/failed")
                        .usernameParameter("userId")
                        .passwordParameter("passwd")
                        // successHandler, failureHandler를 정의한다면 defaultSuccessUrl, failureUrl보다 우선시 된다.
                        .successHandler(((request, response, authentication) -> {
                            System.out.println("authentication : " + authentication);
                            response.sendRedirect("/home");
                        }))
                        .failureHandler(((request, response, exception) -> {
                            System.out.println("exception : " + exception.getMessage());
                            response.sendRedirect("/login");
                        }))
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() { // 설정 파일(yml)보다 우선시 됨.
        UserDetails user = User.withUsername("user")
                .password("{noop}1111")
                .roles("USER").build();
//        UserDetails user2 = User.withUsername("user2")
//                .password("{noop}1111")
//                .roles("USER").build();
//        UserDetails user3 = User.withUsername("user3")
//                .password("{noop}1111")
//                .roles("USER").build();

//        return new InMemoryUserDetailsManager(user, user2, user3);
        return new InMemoryUserDetailsManager(user);

    }
}
