package com.devops.backend.rewear.config.security;

import com.devops.backend.rewear.exceptions.UserNotFoundException;
import com.devops.backend.rewear.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {
    private final UserRepository userRepository;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityBeansInjector(UserRepository userRepository, AuthenticationConfiguration authenticationConfiguration) {
        this.userRepository = userRepository;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username -> {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("user not found with username "+ username));
        });
    }
}
