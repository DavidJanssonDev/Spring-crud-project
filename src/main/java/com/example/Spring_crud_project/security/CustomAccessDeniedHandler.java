package com.example.Spring_crud_project.security;

import com.example.Spring_crud_project.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Handles requests from authenticated users who lack the required permissions.
 *
 * <p>Implements {@link AccessDeniedHandler} so Spring Security invokes this
 * component when an authenticated principal attempts to access a resource they
 * are not authorized for. Responds with HTTP {@code 403 Forbidden} and a JSON
 * {@link ErrorResponse} body rather than the default HTML error page.</p>
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Writes a structured {@code 403 Forbidden} JSON response.
     *
     * @param request              the request that was denied
     * @param response             the response to write the error into
     * @param accessDeniedException the exception that caused the invocation
     * @throws IOException if writing to the response fails
     */
    @Override
    @NullMarked
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                403,
                "FORBIDDEN",
                "You do not have permission to access this resource",
                request.getRequestURI(),
                null
        );

        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
