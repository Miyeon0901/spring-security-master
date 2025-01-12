//package io.security.springsecuritymaster;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig2 {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http,
//                                                   AuthenticationManagerBuilder builder,
//                                                   AuthenticationConfiguration configuration) throws Exception {
//
//        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class); // httpSecurity가 가지고 있는 AuthenticationManagerBuilder를 가져온다.
//        managerBuilder.authenticationProvider(customAuthenticationProvider()); // 자식 provider manager
//
//        ProviderManager authenticationManager = (ProviderManager) configuration.getAuthenticationManager(); // 부모 provider manager
//        authenticationManager.getProviders().remove(0);
//
//        builder.authenticationProvider(new DaoAuthenticationProvider());
//
//        http
//                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/").permitAll()
//                        .anyRequest().authenticated()) // http 통신에 대한 인가 정책을 설정하겠다. (모든 요청)
//                .formLogin(Customizer.withDefaults())
//                ;
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationProvider customAuthenticationProvider() {
//        return new CustomAuthenticationProvider();
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
