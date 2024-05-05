package elice04_pikacharger.pikacharger.security.jwt;


import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
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
        Set<Role> roles = payload.getRoles();
        if(roles == null) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getKey()))
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
