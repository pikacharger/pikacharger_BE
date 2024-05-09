package elice04_pikacharger.pikacharger.domain.user.entity;


import elice04_pikacharger.pikacharger.security.jwt.MyTokenPayload;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Builder
@RequiredArgsConstructor
public class CustomUserDetails implements Authentication {
    private final MyTokenPayload myTokenPayload;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return user.getRoles().stream()
//                .map(role ->
//                        new SimpleGrantedAuthority(role.getKey()))
//                .collect(Collectors.toList());
        return null;
    }
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return myTokenPayload.getUserId();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }



}
