package com.crm.backend.config;

import com.crm.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private CommonConfig commonconfig;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(commonconfig.getEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(authorize-> authorize
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/admin/dashboard/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/createAdmin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/admin/createCustomer").hasAuthority("ADMIN")
                    .requestMatchers("/user/sales_representative/createCustomer/**").hasAuthority("SALESREPRESENTATIVE")
                        .requestMatchers("/user/allSalesReps/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/create_sales-representative/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                ).formLogin(withDefaults()).httpBasic(withDefaults()).csrf(csrf->csrf.disable());
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }


}
