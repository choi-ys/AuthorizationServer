package io.example.authorization.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        String password = this.passwordEncoder().encode("pass");
        log.debug("PasswordEncoder password : [{}] ", password);                    // {bcrypt}$2a$10$q6JJMlG7Q7Gt4n/76ydvp.Vk9pWVcTfCQ4NtWyBzNtWOmefYNw/wO
        log.debug("PasswordEncoder password : [{}] ", this.passwordEncoder().encode("secret"));                    // {bcrypt}$2a$10$goA9F/Q./Ml8lYvuO1tj6OKA5K6VVM/jmUcdIp1AMzqtXHsuo68/W

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password(password).roles("USER").build());
        manager.createUser(User.withUsername("admin").password("{noop}pass").roles("USER", "ADMIN").build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
            .authorizeRequests()
                .anyRequest().authenticated()
                    .and()
            .formLogin()
                    .and()
            .httpBasic()
        ;
    }
}