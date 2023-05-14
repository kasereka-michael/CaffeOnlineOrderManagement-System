package com.webgroupEproject.myproject23526.WebSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity  {

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/**", "/sign/**","/log/**").permitAll()
                        .requestMatchers("/homepage","/ordersubmission/**").permitAll()
                        .requestMatchers("/dashboard").hasAuthority("ROLE_ADMIN")

                ).formLogin(
                        form -> form
                                .loginPage("/LogingSignUpPage")
                                .loginProcessingUrl("/")
                                .defaultSuccessUrl("/homepage")
                                .permitAll()
                ).logout(
                        logout -> {
                            try {
                                logout
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/LogingSignUpPage?logout"))
                                        .permitAll()
                                        .and()
                                        .exceptionHandling().accessDeniedPage("/access-denied");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }




}
