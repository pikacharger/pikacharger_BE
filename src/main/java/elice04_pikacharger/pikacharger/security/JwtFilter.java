package elice04_pikacharger.pikacharger.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtProvider jwtProvider;

    private AuthenticationManager authenticationManager;

    @PostConstruct
    public void init() {this.authenticationManager = new ProviderManager(jwtProvider);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(jwtUtil.allowedUrls).anyMatch(item -> item.equalsIgnoreCase(request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = jwtUtil.extractJwtFromRequest(request);

            if (token != null && jwtUtil.validateToken(token)) {
                MyTokenAuthentication authenticationRequest = new MyTokenAuthentication(token);
                Authentication authentication = authenticationManager.authenticate(authenticationRequest);

                if (!authentication.isAuthenticated()) {
                    throw new BadCredentialsException("Invalid username or password");
                }

                SecurityContextHolder.getContext().setAuthentication(authenticationRequest);
            }
        } catch (ExpiredJwtException e) {
            SecurityContextHolder.clearContext();

            response.sendError(401, e.getMessage());
            return ;
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(400);
            return ;
        }

        filterChain.doFilter(request, response);
    }

}
