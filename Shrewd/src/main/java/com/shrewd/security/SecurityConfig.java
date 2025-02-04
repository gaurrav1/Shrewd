package com.shrewd.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public FilterRegistrationBean<TenantInterceptor> tenantInterceptorFilter(TenantInterceptor tenantInterceptor) {
        FilterRegistrationBean<TenantInterceptor> registrationBean = new FilterRegistrationBean<>(tenantInterceptor);

        // Setting order to -100 to ensure it runs before other filters (like Spring Security's filters)
        registrationBean.setOrder(-100); // This order value makes it execute before Spring Security

        return registrationBean;
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/organization/auth/register", "/hello").permitAll()
                        .requestMatchers("/organization/**", "/user/auth/register").hasAuthority("ORGANIZATION")
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "ORGANIZATION")
                        .requestMatchers("/user/**").hasAnyAuthority("ADMIN", "HR", "MANAGER", "EMPLOYEE")
                        .anyRequest().authenticated()
                );

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}