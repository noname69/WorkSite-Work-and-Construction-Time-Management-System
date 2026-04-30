package online.nonamelab.WorkSite.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import online.nonamelab.WorkSite.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET = "my-super-secret-key-my-super-secret-key"; // >= 32 chars
    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(User user) {

        return Jwts.builder()
//                .setSubject(user.getEmail())
                .setSubject(String.valueOf(user.getId()))
                .claim("role", user.getRole().name()) //???????
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return parse(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return parse(token).getBody().get("role", String.class);
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isValid(String token, UserPrincipal userPrincipal) {
        String userId = extractSubject(token);
//        System.out.println("user id: " + userId);
//        System.out.println("userPrincipal id: " + userPrincipal.getId());
        return userId.equals(userPrincipal.getId().toString()) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return parse(token).getBody().getExpiration().before(new Date());
    }


    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
