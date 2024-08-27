package com.rafalstefanski.springdailynews.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(DataSource dataSource, UserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/register", "/css/**", "/js/**").permitAll()
                                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                                .requestMatchers("/verify-token").permitAll()
                                .requestMatchers(error -> error.getRequestURI().startsWith("/error")).permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/news").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers("/news/edit/**").hasAuthority("ROLE_ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin.permitAll()  // for custom login page with remember me checkbox
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("secret")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(userDetailsService)
                        .tokenRepository(persistentTokenRepository())
                )
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );
        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

}
