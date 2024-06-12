package com.hslcreator.LibraryAPI.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        String[] publicApis = {
                "/authentication/**",
                "/swagger-ui/**",
        };
        return web -> web.ignoring().requestMatchers(publicApis);
    }

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        authorize -> authorize.requestMatchers("/authentication/sign-up", "/swagger-ui/**").permitAll()
//                                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAutFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request ->
                        new CorsConfiguration().applyPermitDefaultValues()
                ))
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(
                        management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
