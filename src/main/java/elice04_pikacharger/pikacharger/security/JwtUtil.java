package elice04_pikacharger.pikacharger.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey secretKey;

    public final String[] allowedUrls = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/",
            "/user/**",
            "/error",
            "/index.html",
            "/swagger-resources",
            "/swagger-resources/**",
            "/webjars/**"

    };

    @PostConstruct
    public void init() {this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}

    public String generateToken(MyTokenPayload payload) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claim("email",payload.getEmail())
                .claim("username",payload.getName())
                .claim("roles",payload.getRoles())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();

    }

    public MyTokenPayload verify(String token) throws BadCredentialsException {
        try{
            Claims payload = Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = payload.get("email", String.class);
            String username = payload.get("username", String.class);
            List<String> roles = (List<String>) payload.get("roles", List.class);

            MyTokenPayload myTokenPayload = new MyTokenPayload(email,username,roles);
            return myTokenPayload;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("Invalid token");
        }
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        // Extract JWT from the Authorization header
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }

}
