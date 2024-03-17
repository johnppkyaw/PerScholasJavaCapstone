//package dev.johnkyaw.medmx.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
//
//        http
//                .authorizeHttpRequests(auth ->
//                        auth
//                                .requestMatchers("/", "/login*",
//                                        "/css/*", "/js/*", "/sign-up", "/signup-process").permitAll()
//                                .requestMatchers("/home")
//                                .hasAnyRole("ADMIN","USER").anyRequest().authenticated()
//                )
//                .formLogin(
//                        form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/home")
//                                .permitAll()
//                ).logout(
//                        logout -> logout
//                                .invalidateHttpSession(true)
//                                .clearAuthentication(true)
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .permitAll());
//        return http.build();
//    }
//}
