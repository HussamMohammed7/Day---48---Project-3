package com.example.project3.Config;


import com.example.project3.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {


    private final MyUserDetailsService myUserDetailsService;


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        return daoAuthenticationProvider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/**").permitAll()
                .requestMatchers(
                        "/api/v1/customer/update/**",
                        "/api/v1/customer/accounts/deposit",
                        "/api/v1/customer/createAccount",
                        "/api/v1/customer/operations/**",
                        "/api/v1/customer/accounts/**",
                        "/api/v1/account/create/**"
                ).hasAuthority("CUSTOMER")
                .requestMatchers(
                        "/api/v1/customer/get",
                        "/api/v1/user/get/users",
                          "api/v1/account/delete/",
                        "api/v1/account/{customerId}/{accountId}/deactivate",
                        "api/v1/user/delete/",
                        "/api/v1/employee/get",
                        "api/v1/customer/delete/"
                ).hasAuthority("ADMIN")
                .requestMatchers(
                        "/api/v1/employee/update",
                        "/api/v1/employee/delete",
                        "/api/v1/user/get/users"

                ).hasAuthority("EMPLOYEE")


                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
