//package com.ne.template.configs;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ne.template.security.CustomUserDetailsService;
//import com.ne.template.security.JwtAuthenticationEntryPoint;
//import com.ne.template.security.JwtAuthenticationFilterChain;
//import com.ne.template.utils.ApResponse;
//import com.ne.template.utils.ApiResponse;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
//public class SecurityConfig {
//    private final CustomUserDetailsService userService;
//    private final JwtAuthenticationEntryPoint authEntryPoint;
//
//    private static final String[] AUTH_WHITELIST = {
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui/**",
//            "/webjars/**",
//            "/swagger-ui.html",
//            "/v3/api-docs/**",
//            "/actuator/*",
//            "/api/v1/auth/**",
//            "/api/v1/roles/**",
//            "/api/v1/users/**",
//            "/api/users/is-code-valid",
//            "/api/documents/download/**",
//    };
//
//    @Bean
//    public AuthenticationEntryPoint authenticationErrorHandler() {
//        return (request, response, ex) -> {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            ServletOutputStream out = response.getOutputStream();
//            new ObjectMapper().writeValue(out, new ApiResponse(false, "Invalid or missing token"));
//
//            out.flush();
//        };
//    }
//
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return (request, response, ex) -> {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            ServletOutputStream out = response.getOutputStream();
//            new ObjectMapper().writeValue(out, new ApResponse<String>("You are not allowed to access this resource.", (Object) "", HttpStatus.FORBIDDEN));
//            out.flush();
//        };
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .anyRequest().authenticated())
//                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint));
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//
//    @Bean
//    public JwtAuthenticationFilterChain jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilterChain();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
//            throws Exception {
//        return config.getAuthenticationManager();
//    }
//}
//


//package com.ne.template.configs;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ne.template.security.CustomUserDetailsService;
//import com.ne.template.security.JwtAuthenticationFilterChain;
//import com.ne.template.utils.ApResponse;
//import com.ne.template.utils.ApiResponse;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
//public class SecurityConfig {
//    private final CustomUserDetailsService userService;
//    private final JwtAuthenticationFilterChain jwtAuthenticationFilter;
//
//    @Autowired
//    public SecurityConfig(CustomUserDetailsService userService, JwtAuthenticationFilterChain jwtAuthenticationFilter) {
//        this.userService = userService;
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//
//    private static final String[] AUTH_WHITELIST = {
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui/**",
//            "/webjars/**",
//            "/swagger-ui.html",
//            "/v3/api-docs/**",
//            "/actuator/*",
//            "/api/v1/auth/**",
//            "/api/v1/roles/**",
//            "/api/v1/users/**",
//            "/api/users/is-code-valid",
//            "/api/documents/download/**",
//    };
//
//    @Bean
//    public AuthenticationEntryPoint authenticationErrorHandler() {
//        return (request, response, ex) -> {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            ServletOutputStream out = response.getOutputStream();
//            new ObjectMapper().writeValue(out, new ApiResponse(false, "Invalid or missing token"));
//            out.flush();
//        };
//    }
//
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return (request, response, ex) -> {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            ServletOutputStream out = response.getOutputStream();
//            new ObjectMapper().writeValue(out, new ApResponse<String>("You are not allowed to access this resource.", (Object) "", HttpStatus.FORBIDDEN));
//            out.flush();
//        };
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .anyRequest().authenticated())
//                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint(authenticationErrorHandler())
//                        .accessDeniedHandler(accessDeniedHandler()))
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
//            throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public JwtAuthenticationFilterChain jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilterChain();
//    }
//}

package com.ne.template.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ne.template.security.CustomUserDetailsService;
import com.ne.template.security.JwtAuthenticationEntryPoint;
import com.ne.template.security.JwtAuthenticationFilterChain;
import com.ne.template.utils.ApResponse;
import com.ne.template.utils.ApiResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final CustomUserDetailsService userService;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, CustomUserDetailsService userService) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.userService = userService;
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/actuator/*",
            "/api/v1/auth/**",
            "/api/v1/roles/**",
            "/api/v1/users/**",
            "/api/users/is-code-valid",
            "/api/documents/download/**",
    };

    @Bean
    public JwtAuthenticationFilterChain jwtAuthenticationFilterChain() {
        return new JwtAuthenticationFilterChain();
    }

    @Bean
    public AuthenticationEntryPoint authenticationErrorHandler() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ServletOutputStream out = response.getOutputStream();
            new ObjectMapper().writeValue(out, new ApiResponse(false, "Invalid or missing token"));

            out.flush();
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ServletOutputStream out = response.getOutputStream();
            new ObjectMapper().writeValue(out, new ApResponse<>("You are not allowed to access this resource.", (Object) "", HttpStatus.FORBIDDEN));
            out.flush();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilterChain(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean

    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
