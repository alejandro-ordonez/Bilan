package org.bilan.co.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        // Log the incoming request
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String fullUrl = queryString != null ? uri + "?" + queryString : uri;

        log.info(">>> [{}] {} - Started", method, fullUrl);

        try {
            // Continue with the filter chain
            filterChain.doFilter(request, response);
        } finally {
            // Log the response
            long duration = System.currentTimeMillis() - startTime;
            int status = response.getStatus();

            log.info("<<< [{}] {} - Completed with status {} in {}ms",
                    method, fullUrl, status, duration);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Optional: Skip logging for certain paths like actuator endpoints
        String path = request.getRequestURI();
        return path.startsWith("/actuator") || path.startsWith("/swagger-ui") || path.startsWith("/api-docs");
    }
}
