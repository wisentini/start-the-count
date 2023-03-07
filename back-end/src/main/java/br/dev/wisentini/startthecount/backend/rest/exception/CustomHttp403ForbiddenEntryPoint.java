package br.dev.wisentini.startthecount.backend.rest.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomHttp403ForbiddenEntryPoint implements AuthenticationEntryPoint {

    private static final int STATUS_CODE = 403;

    private static final String CONTENT_TYPE = "application/json";

    private static final String CHARSET = "UTF-8";

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {
        response.setStatus(STATUS_CODE);
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARSET);
        response.getWriter().write(String.format("{\"status\": \"FORBIDDEN\", \"errors\": [{\"message\": \"O usuário precisa estar autenticado e ser elegível para que a requisição seja atendida.\"}], \"timestamp\": \"%s\"}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
    }
}
