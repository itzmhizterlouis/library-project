package com.hslcreator.LibraryAPI.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hslcreator.LibraryAPI.controllerutils.AppResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String JWT_GENERIC_ERROR = "Unable to authenticate request";

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException ex) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        log.error(ex.getMessage(), ex);
        String jsonErrorResponse = OBJECT_MAPPER.writeValueAsString(AppResponseUtil.buildErrorResponse(JWT_GENERIC_ERROR));
        httpServletResponse.getWriter().write(jsonErrorResponse);
    }
}
