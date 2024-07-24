package com.andersen.ticketToRide.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A custom implementation of {@link AuthenticationSuccessHandler} that handles
 * successful authentication events. This handler redirects users to a URL path
 * based on their username after a successful login.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Called when a user successfully authenticates. This method redirects the user
     * to a URL path determined by their username.
     *
     * @param request the {@link HttpServletRequest} object containing the request the client made
     * @param response the {@link HttpServletResponse} object containing the response the server sends
     * @param authentication the {@link Authentication} object containing user details and authorities
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        response.sendRedirect("/" + username);
    }
}