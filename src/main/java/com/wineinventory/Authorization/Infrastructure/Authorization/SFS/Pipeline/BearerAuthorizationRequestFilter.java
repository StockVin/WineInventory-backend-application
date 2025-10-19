package com.wineinventory.Authorization.Infrastructure.Authorization.SFS.Pipeline;

import com.wineinventory.Authorization.Infrastructure.Authorization.SFS.Model.UsernamePasswordAuthenticationTokenBuilder;
import com.wineinventory.Authorization.Infrastructure.Tokens.JWT.BearerTokenService;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.Authentication;

import java.io.IOException;

/**
 * Bearer Authorization Request Filter.
 * <p>
 * This class is responsible for filtering requests and setting the user authentication.
 * It extends the OncePerRequestFilter class.
 * </p>
 * @see OncePerRequestFilter
 */
public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BearerAuthorizationRequestFilter.class);
    private final BearerTokenService tokenService;

    @Qualifier("defaultUserDetailsService")
    private final UserDetailsService userDetailsService;

    public BearerAuthorizationRequestFilter(BearerTokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method is responsible for filtering requests and setting the user authentication.
     * @param request The request object.
     * @param response The response object.
     * @param filterChain The filter chain object.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        String method = request.getMethod();
        LOGGER.info("AuthFilter -> {} {}", method, path);

        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = tokenService.getBearerTokenFrom(request);
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

            if (token == null || token.isBlank()) {
                LOGGER.info("No Bearer token found in Authorization header");
            } else if (tokenService.validateToken(token)) {
                String username = tokenService.getUsernameFromToken(token);
                boolean sameUserAlreadySet = false;
                if (currentAuth != null && currentAuth.isAuthenticated()) {
                    try {
                        sameUserAlreadySet = username.equalsIgnoreCase(currentAuth.getName());
                    } catch (Exception ignored) { }
                }

                if (sameUserAlreadySet) {
                    LOGGER.debug("Authentication already set for user: {}", username);
                } else {
                    LOGGER.info("Token is valid for user: {} — setting authentication", username);
                    var userDetails = userDetailsService.loadUserByUsername(username);
                    SecurityContextHolder.getContext().setAuthentication(
                            UsernamePasswordAuthenticationTokenBuilder.build(userDetails, request));
                }
            } else {
                LOGGER.info("Token is not valid");
            }
        } catch (Exception e) {
            LOGGER.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return path.equals("/api/v1/sign-up")
                || path.equals("/api/v1/sign-in")
                || path.equals("/api/v1/accounts/sign-up")
                || path.equals("/api/v1/accounts/login")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }
}