//package io.security.springsecuritymaster;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        AuthenticationManager authenticationManager = builder.build(); // 최초
////        AuthenticationManager authenticationManager = builder.getObject(); // 빌드 이후
//
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/api/login").permitAll()
//                        .anyRequest().authenticated()) // http 통신에 대한 인가 정책을 설정하겠다. (모든 요청)
//                .authenticationManager(authenticationManager)
//                .addFilterBefore(customAuthenticationFilter(http, authenticationManager), UsernamePasswordAuthenticationFilter.class)
//        ;
//
//        return http.build();
//    }
//
//    public CustomAuthenticationFilter customAuthenticationFilter(HttpSecurity http, AuthenticationManager authenticationManager) {
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(http);
//        customAuthenticationFilter.setAuthenticationManager(authenticationManager);
//
//        return customAuthenticationFilter;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() { // 설정 파일(yml)보다 우선시 됨.
//        UserDetails user = User.withUsername("user")
//                .password("{noop}1111")
//                .roles("USER").build();
//        return new InMemoryUserDetailsManager(user);
//
//    }
//}
