package online.nonamelab.WorkSite.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import online.nonamelab.WorkSite.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user) {

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public String extractEmail(String token) {
//        return getClaims(token).getSubject();
//    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public Long extractUserId(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

//    public String extractSubject(String token) {
//        return getClaims(token).getSubject();
//    }

    public boolean isTokenValid(String token) {
        try {
            return !getClaims(token)
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
