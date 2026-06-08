package com.example.Spring_crud_project.security;

import com.example.Spring_crud_project.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Handles requests that arrive without valid authentication credentials.
 *
 * <p>Implements {@link AuthenticationEntryPoint} so Spring Security invokes
 * this component whenever a request reaches a protected resource without a
 * valid JWT (i.e. the security context has no {@code Authentication} object).
 * Responds with HTTP {@code 401 Unauthorized} and a JSON {@link ErrorResponse}
 * body instead of the default HTML error page.</p>
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Writes a structured {@code 401 Unauthorized} JSON response.
     *
     * @param request       the request that triggered the authentication failure
     * @param response      the response to write the error into
     * @param authException the exception that caused the invocation
     * @throws IOException if writing to the response fails
     */
    @Override
    @NullMarked
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                401,
                "UNAUTHORIZED",
                "You must be logged in to access this resource",
                request.getRequestURI(),
                null
        );

        new ObjectMapper().writeValue(response.getWriter(), errorResponse);
    }
}
