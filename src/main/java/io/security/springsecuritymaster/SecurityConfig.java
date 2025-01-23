package io.security.springsecuritymaster;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/user").hasAuthority("ROLE_USER") // "/user" 엔드포인트에 대해 "USER" 권한을 요구한다.
                        .requestMatchers("/myPage/**").hasRole("USER") // "/myPage" 및 하위 디렉터리에 대해 "USER" 권한을 요구한다. Ant 패턴 사용.
                        .requestMatchers(HttpMethod.POST).hasAuthority("ROLE_WRITE") // POST 메소드를 사용하는 모든 요청에 대해 "write" 권한을 요구한다.
                        .requestMatchers(new AntPathRequestMatcher("/manager/**")).hasAuthority("ROLE_MANAGER") // "/manager" 및 하위 디렉터리에 대해 "MANAGER" 권한을 요구한다. AntPath 사용
                        .requestMatchers(new MvcRequestMatcher(introspector, "/admin/payment")).hasAuthority("ROLE_ADMIN") // 문자열로 설정하면 내부적으로 MvcRequestMatcher를 만들어줌.
                        .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // "/admin" 및 하위 디렉터리에 대해 "ADMIN" 또는 "MANAGER" 중 하나의 권한을 요구한다.
                        .requestMatchers(new RegexRequestMatcher("/resource/[A-Za-z0-9]*", null)).hasAuthority("ROLE_MANAGER") // 정규 표현식을 사용하여 "/resource/[A-Za-z0-9]*"
                        .anyRequest().authenticated() // 위에서 적용한 규칙 외의 모든 요청은 인증을 필요로 한다.
                )
                .formLogin(Customizer.withDefaults()) // 인증을 받지 못했을 경우에 formLogin 방식(default)으로 인증을 받는다.
                .csrf(AbstractHttpConfigurer::disable)
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
