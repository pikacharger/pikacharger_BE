package elice04_pikacharger.pikacharger.security.jwt;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class MyTokenAuthentication implements Authentication {
    private final String token;

    @Setter
    private MyTokenPayload payload = new MyTokenPayload();
    @Setter
    private boolean authenticated = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(payload.getRole()) // Role이 null이 아닌지 확인
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }


    @Override
    public Object getCredentials() {return null;}

    @Override
    public MyTokenPayload getDetails() {return payload;}

    @Override
    public String getPrincipal() {return payload.getEmail();}

    @Override
    public String getName() { return payload.getName();}

}
