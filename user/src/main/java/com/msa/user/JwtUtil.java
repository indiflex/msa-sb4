package com.msa.user;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Map;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String JwtSecret) {
        secretKey = Keys.hmacShaKeyFor(JwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public Map<String, Object> validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token Expired");
        } catch (JwtException e) {
            throw new JwtException("Jwt Error");
        } catch (Exception e) {
            throw new JwtException("Error: " + e.getMessage());
        }
    }

    public Map<String, Object> authenticationToClaims(Authentication authentication) {
        UserDTO dto = (UserDTO) authentication.getPrincipal();

        if (dto == null) {
            throw new JwtException("Invalid Authentication");
        }

        System.out.println("***** dto.getRoleNames() = " + dto.getRoleNames());
        Map<String, Object> claims = new UserDTO(dto.getId(),
                dto.getEmail(), "", dto.getName(), dto.getRoleNames()
        ).getClaims();

        claims.put("accessToken", generateToken(claims, 10));
        claims.put("refreshToken", generateToken(claims, 60 * 24));

        return claims;
    }

    public String generateToken(Map<String, Object> valueMap, int min) {
        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .claims().add(valueMap).and()
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(secretKey)
                .compact();
    }

}
