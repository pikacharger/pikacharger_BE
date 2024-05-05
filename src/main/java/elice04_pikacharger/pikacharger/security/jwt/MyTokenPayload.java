package elice04_pikacharger.pikacharger.security.jwt;

import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyTokenPayload {
    private String email;
    private String name;
    private Role role;
}
