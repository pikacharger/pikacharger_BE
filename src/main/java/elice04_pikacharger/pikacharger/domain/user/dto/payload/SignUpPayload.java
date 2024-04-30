package elice04_pikacharger.pikacharger.domain.user.dto.payload;


import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignUpPayload {
    private String username;

    @Email
    private String email;

    private String nickname;
    private String password;
    private String address;
    private String phoneNumber;
    private Long roleId;
    private String chargerType;
    private String profileImage;

}
