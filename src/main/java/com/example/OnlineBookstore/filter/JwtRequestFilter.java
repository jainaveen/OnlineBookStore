package com.example.OnlineBookstore.filter;
import com.example.OnlineBookstore.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractUsername(token);
                    // Normally, you'd set user authentication here
                    System.out.println("Authenticated user: " + username);
                } else {
                    // Log invalid token
                    System.out.println("Invalid JWT token");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                    response.getWriter().write("Invalid or expired JWT token");
                    return; // Stop further processing
                }
            } catch (Exception e) {
                // Handle exceptions (e.g., signature exceptions, parsing issues)
                System.out.println("Token validation error: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                response.getWriter().write("Invalid or expired JWT token");
                return; // Stop further processing
            }

        }

        chain.doFilter(request, response);
    }
}
