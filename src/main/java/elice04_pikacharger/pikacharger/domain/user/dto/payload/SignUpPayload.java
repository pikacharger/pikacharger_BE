package elice04_pikacharger.pikacharger.domain.user.dto.payload;


import elice04_pikacharger.pikacharger.domain.user.entity.Role;

import lombok.Data;

@Data
public class SignUpPayload {
    private String username;
    private String email;
    private String nickname;
    private String password;
    private String address;
    private String phoneNumber;
    private Role role;

}
