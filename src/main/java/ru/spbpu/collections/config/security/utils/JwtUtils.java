package ru.spbpu.collections.config.security.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.spbpu.collections.model.security.UserDetailsImpl;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static ru.spbpu.collections.utils.DateTimeUtils.now;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${spring.application.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.application.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(final UserDetailsImpl userDetails) {
        return generateTokenFromEmail(userDetails.getEmail());
    }

    public String generateTokenFromEmail(final String email) {

        final Date now = now();
        final SecretKey key = hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtExpirationMs))
                .signWith(key, HS512)
                .compact();
    }

    public String getUserEmailFromJwtToken(final String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(final String authToken) {

        try {
            Jwts.parserBuilder().setSigningKey(hmacShaKeyFor(jwtSecret.getBytes())).build().parseClaimsJws(authToken);
            return true;
        } catch (final SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (final MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (final ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (final UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (final IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
