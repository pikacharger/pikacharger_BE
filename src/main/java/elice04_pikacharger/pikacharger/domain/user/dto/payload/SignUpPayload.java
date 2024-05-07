package elice04_pikacharger.pikacharger.domain.user.dto.payload;


import elice04_pikacharger.pikacharger.domain.user.entity.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpPayload {
    private String username;
    private String email;
    private String nickname;
    private String password;
    private String address;
    private String phoneNumber;

}
