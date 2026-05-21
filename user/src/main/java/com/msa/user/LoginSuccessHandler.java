package com.msa.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@NullMarked
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        System.out.println("*** SuccessHandler.auth=" + authentication);

        Map<String, Object> claims = jwtUtil.authenticationToClaims(authentication);

        ObjectMapper objMapper = new ObjectMapper();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(objMapper.writeValueAsString(claims));
        out.close();
    }
}
