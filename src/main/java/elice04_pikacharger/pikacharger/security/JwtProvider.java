package elice04_pikacharger.pikacharger.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider implements AuthenticationProvider {
    private final JwtUtil tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("authenticate");
        if(supports(authentication.getClass())){
            MyTokenPayload verified = tokenService.verify(((MyTokenAuthentication) authentication).getToken());
            authentication.setAuthenticated(true);
            ((MyTokenAuthentication) authentication).setPayload(verified);
            return authentication;
        } else{
            throw new BadCredentialsException("Bad credentials");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MyTokenAuthentication.class.equals(authentication);
    }

}
