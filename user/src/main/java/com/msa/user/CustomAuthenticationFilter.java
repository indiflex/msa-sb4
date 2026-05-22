package com.msa.user;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@NullMarked
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private static final String[] EXCLUDE_PATTERNS = {
            "/api/users/login",
//            "/api/users/hello",
            "/api/users",
            "/internal/**",
            "/favicon.ico",
            "/actuator/**",
            "/*.html",
            "/swagger-ui/**"
    };

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        System.out.println("** path = " + path);
        return Arrays.stream(EXCLUDE_PATTERNS)
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(response, "Missing or invalid Authorization header");
            return;
        }

        try {
            Map<String, Object> claims = jwtUtil.validateToken(authHeader.substring(7));

            Integer id = (Integer) claims.get("id");
            UserDTO dto = new UserDTO(
                    id.longValue(),
                    (String) claims.get("email"),
                    "",
                    (String) claims.get("name"),
                    (List<String>) claims.get("roleNames")
            );

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(dto, null, dto.getAuthorities())
            );

        } catch (Exception e) {
            sendError(response, "ERROR_ACCESS_TOKEN:" + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(objectMapper.writeValueAsString(Map.of("error", message)));
        out.close();
    }
}
