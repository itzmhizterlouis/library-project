package com.hslcreator.LibraryAPI.config;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserDetailsService userDetailsService;
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/sign-up")) {
//
//            filterChain.doFilter(request, response);
//
//        }
//
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userMatric;
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//
//            filterChain.doFilter(request, response);
//        }
//
//        jwt = authHeader.substring(7);
//        userMatric = jwtService.extractUsername(jwt);// TODO: extract user matric from jwt token
//
//        if (userMatric != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userMatric);
//
//            if (jwtService.isTokenValid(jwt, userDetails)) {
//
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities()
//                );
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtTokenProvider;
    private final UserDetailsService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = getTokenFromHeader(request);
            if (StringUtils.hasText(token)) {
                jwtTokenProvider.validateToken(token);

                String matricNumber = jwtTokenProvider.extractUsername(token);

                var user = userService.loadUserByUsername(matricNumber);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");

        if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer")) {
            return jwt.substring(7);
        }
        return null;
    }
}
