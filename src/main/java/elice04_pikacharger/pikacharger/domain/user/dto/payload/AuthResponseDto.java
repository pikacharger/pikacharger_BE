package elice04_pikacharger.pikacharger.domain.user.dto.payload;


import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthResponseDto {
    private String email;
    private String jwtToken;
    private Role role;
}
