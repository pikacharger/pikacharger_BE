package elice04_pikacharger.pikacharger.security.oauth;

import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    private Long userId;
    private String email;
    private String name;
    private Set<Role> roles;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String email, String name , Set<Role> roles){
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.name = name;
        this.roles = roles;
    }
}
