package com.teacode.discoveryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

       authenticationManagerBuilder.
               inMemoryAuthentication()
               .withUser("eureka").password("password")
               .authorities("USER");

       return (InMemoryUserDetailsManager) authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }
}
