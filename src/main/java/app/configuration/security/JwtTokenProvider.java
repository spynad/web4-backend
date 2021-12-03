package app.configuration.security;

import app.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final String secret = "N6Fi8#@2fc4c1x!d";
    private final String issuer = "lab4_spynad";

    public JwtTokenProvider() {}

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public String createJwtToken(User user) {
        Date date = new Date();
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer(issuer)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + 1000 * 60 * 30))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Empty string: {}", e.getMessage());
        }

        return false;
    }
}