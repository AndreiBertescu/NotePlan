package com.noteplan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableAsync
public class SecurityConfig {

    /**
     * UserDetailsService interface.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * returns a password hash encoder.
     *
     * @return PasswordEncoder.
     */
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates and configures a DaoAuthenticationProvider bean used for authentication in Spring Security.
     *
     * This provider uses the UserDetailsService for user lookup and the PasswordEncoder for password 
     * encoding/validation. It is typically used for authenticating users with a database-backed user store.
     *
     * @return DaoAuthenticationProvider.
     */
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    /**
     * configures the security settings for every authority.
     *
     * @param http.
     *
     * @return SecurityFilterChain.
     */
    @Bean
    SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/", "/home", "/js/**", "/css/**").permitAll();
                    auth.requestMatchers("/", "/index", "/register", "/login", "/confirmation", "/verificationStatus",
                            "/confirm-account", "/resendEmail").permitAll();
                    auth.anyRequest().hasRole("USER");
                }).formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/dashboard", true))
                .logout((logout) -> logout.deleteCookies("remove").invalidateHttpSession(false)
                        .clearAuthentication(true).logoutSuccessUrl("/"))
                .rememberMe(Customizer.withDefaults()).build();
    }
}
