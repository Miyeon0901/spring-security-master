package io.security.springsecuritymaster;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
        XorCsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new XorCsrfTokenRequestAttributeHandler();
        csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName(null); // 토큰을 즉시 로딩. 핸들러에서 csrfToken.getParameterName() 실행됨.

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/csrf", "/ignoreCsrf", "csrfToken").permitAll()
                        .anyRequest().authenticated()) // http 통신에 대한 인가 정책을 설정하겠다. (모든 요청)
                .formLogin(Customizer.withDefaults()) // 인증을 받지 못했을 경우에 formLogin 방식(default)으로 인증을 받는다.
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/ignoreCsrf"))
//                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository))
                .csrf(csrf -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // client에서 script로 cookie를 읽어오고 싶을때. 보안상 좋지는 않음.
                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                )
        ;
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() { // 설정 파일(yml)보다 우선시 됨.
        UserDetails user = User.withUsername("user")
                .password("{noop}1111")
                .roles("USER").build();
        return new InMemoryUserDetailsManager(user);

    }
}
