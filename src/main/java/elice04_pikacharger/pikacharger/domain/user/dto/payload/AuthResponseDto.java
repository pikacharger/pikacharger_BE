package elice04_pikacharger.pikacharger.domain.user.dto.payload;


import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class AuthResponseDto {
    private String email;
    private String jwtToken;
    private String refreshToken;
    private Set<Role> roles;


}
