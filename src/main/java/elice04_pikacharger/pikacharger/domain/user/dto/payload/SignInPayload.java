package elice04_pikacharger.pikacharger.domain.user.dto.payload;

import lombok.Data;

@Data
public class SignInPayload {

    private String email;
    private String password;
}
