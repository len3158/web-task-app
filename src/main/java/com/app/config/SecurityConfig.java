//package com.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                // Définir les règles pour les requêtes (par exemple, quelles routes sont sécurisées)
//                .authorizeRequests()
//                .antMatchers("/public/**").permitAll() // Autoriser l'accès à tous pour les chemins sous /public
//                .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
//                .and()
//                // Configurer la page de connexion et le traitement du logout
//                .formLogin()
//                .loginPage("/login") // Spécifier la page de connexion personnalisée
//                .permitAll() // Permettre à tous l'accès à la page de connexion
//                .and()
//                .logout()
//                .permitAll(); // Permettre à tous l'accès au logout
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                // Configurer l'authentification en mémoire pour le test (à remplacer par une authentification basée sur la base de données ou autre)
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER");
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // Utiliser BCryptPasswordEncoder pour le hachage des mots de passe
//        return new BCryptPasswordEncoder();
//    }
//}
